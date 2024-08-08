import { Client } from '@stomp/stompjs';
import { accessTokenState, isLoginState } from 'atoms/UserAtoms';
import { useEffect, useState } from "react";
import { useRecoilValue } from 'recoil';

interface Position {
    latitude: number;
    longitude: number;
}

export const useLocation = () => {
    const [coords, setCoords] = useState<Position>({
        latitude: 0,
        longitude: 0
    });
    const [error, setError] = useState<string | undefined>()
    const [client, setClient] = useState<Client | undefined>(undefined);
    const isLogin = useRecoilValue(isLoginState);
    const accessToken = useRecoilValue(accessTokenState)

    useEffect(() => {
        fetchLocation();
    }, []);

    const fetchLocation = () => {
        if ('geolocation' in navigator) {
            navigator.geolocation.getCurrentPosition(
                (position) => {
                    setCoords(position.coords);


                },
                (error) => {
                    setError(error.message);
                },
            );
        } else {
            setError('위치 정보를 가져올 수 없습니다.');
        }
    }

    useEffect(() => {
        if ((coords.latitude === 0 && coords.longitude === 0)) return;
        if (!client || !client.connected) return;

        client.publish({
            destination: '/pub/location/update',
            body: JSON.stringify({geo: {
                    latitude: coords.latitude + 0.0004,
                    longitude: coords.longitude - 0.0002
                }
            }),
            // headers: {
            //     Authorization: `Bearer ${accessToken}`
            // }
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
                    console.log(message)
                })
            }
        })
        client.activate()
        setClient(client)
    }, [isLogin])

    return { coords, error }
}