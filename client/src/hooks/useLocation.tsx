import { Client } from '@stomp/stompjs';
import { accessTokenState, isLoginState } from 'atoms/UserAtoms';
import { useEffect, useState } from "react";
import { useRecoilValue } from 'recoil';

interface Position {
    latitude: number;
    longitude: number;
}

interface UseLocationProps {
    refetch: () => void
}

export const useLocation = ({refetch}: UseLocationProps) => {
    const [coords, setCoords] = useState<Position>({
        latitude: 0,
        longitude: 0
    });
    const [error, setError] = useState<string | undefined>()
    const [client, setClient] = useState<Client | undefined>(undefined);
    const isLogin = useRecoilValue(isLoginState);
    const accessToken = useRecoilValue(accessTokenState)

    const fetchLocation = () => {
        if ('geolocation' in navigator) {
            navigator.geolocation.getCurrentPosition(
                (position) => {
                    console.log(position.coords)
                    setCoords((prev) => {
                        if (computeDistance(prev, position.coords) < 0.1) return prev;
                        return {
                            latitude: position.coords.latitude,
                            longitude: position.coords.longitude
                        }
                    });
                },
                (error) => {
                    console.log(error)
                    setError(error.message);
                },
            );
        } else {
            setError('위치 정보를 가져올 수 없습니다.');
        }
    }

    useEffect(() => {
        const parseLocation = setInterval(() => {
            console.log('fetching location')
            fetchLocation();
        }, 3000)

        return () => {
            clearInterval(parseLocation);
        }
    }, []);

    function computeDistance(startCoords: Position, destCoords: Position) {
        var startLatRads = degreesToRadians(startCoords.latitude);
        var startLongRads = degreesToRadians(startCoords.longitude);
        var destLatRads = degreesToRadians(destCoords.latitude);
        var destLongRads = degreesToRadians(destCoords.longitude);
    
        var Radius = 6371;
        var distance = Math.acos(Math.sin(startLatRads) * Math.sin(destLatRads) + 
                        Math.cos(startLatRads) * Math.cos(destLatRads) *
                        Math.cos(startLongRads - destLongRads)) * Radius;
    
        return distance;
    }
    
    function degreesToRadians(degrees: number) {
        return (degrees * Math.PI)/180;
    }

    useEffect(() => {
        if ((coords.latitude === 0 && coords.longitude === 0)) return;
        if (!client || !client.connected) return;

        client.publish({
            destination: '/pub/location/update',
            body: JSON.stringify({
                latitude: coords.latitude,
                longitude: coords.longitude
            }),
        })
    }, [coords, client, client?.connected])

    useEffect(() => {
        if (!isLogin) return;

        const client = new Client({
            brokerURL: process.env.REACT_APP_BACKEND_SOCKET_HOST,
            connectHeaders: {
                Authorization: `Bearer ${accessToken}`
            },
            onConnect: () => {
                client!!.subscribe('/sub/location/update', (message) => {
                    refetch();
                })
            }
        })
        client.activate()
        setClient(client)
    }, [isLogin])

    return { coords, error }
}