import BackIcon from 'icons/BackIcon';
import UserPickIcon from 'icons/UserPickIcon';
import LocationCircle from 'components/LocationPage/LocationCircle';
import LocationCheckModal from 'components/LocationPage/LocationCheckModal';
import LocationWatchModal from 'components/LocationPage/LocationWatchModal';

import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import LocationModal from 'components/modals/LocationModal';

const LocationAlarm = () => {
  const nav = useNavigate();
  const [dot, setDot] = useState('');
  const [latitude, setLatitude] = useState<number | null>(null);
  const [longitude, setLongitude] = useState<number | null>(null);
  const [watchLatitude, setwatchLatitude] = useState<number | null>(null);
  const [watchLongitude, setwatchLongitude] = useState<number | null>(null);
  const [error, setError] = useState<string | null>(null);

  // search text
  useEffect(() => {
    const interval = setInterval(() => {
      setDot((prevDots) => (prevDots === '...' ? '' : prevDots + '.'));
    }, 700);

    return () => clearInterval(interval); // 컴포넌트가 언마운트될 때 인터벌 정리
  }, []);

  // 위치 정보 가져오기 (렌더링 될 때만)
  useEffect(() => {
    if ('geolocation' in navigator) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          setLatitude(position.coords.latitude);
          setLongitude(position.coords.longitude);
        },
        (error) => {
          setError(error.message);
        },
      );
    } else {
      setError('위치 정보를 가져올 수 없습니다.');
    }
  }, []);

  // 위치 정보 가져오기 (지속적으로 추적)
  useEffect(() => {
    if ('geolocation' in navigator) {
      navigator.geolocation.watchPosition(
        (position) => {
          setwatchLatitude(position.coords.latitude);
          setwatchLongitude(position.coords.longitude);
        },
        (error) => {
          setError(error.message);
        },
      );
    } else {
      setError('위치 정보를 가져올 수 없습니다.');
    }
  }, []);

  return (
    <div>
      <div
        className="flex flex-row items-center m-2 cursor-pointer"
        onClick={() => nav(-1)}
      >
        <BackIcon />
      </div>
      <div className="relative flex justify-center items-center">
        <div className="relative">
          <LocationCircle />
          <UserPickIcon
            width={70}
            height={70}
            gen="female"
            className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2"
          />
        </div>
      </div>
      <div className="text-center mt-9">
        <span className="luckiest_guy text-[#3D6CE6] text-3xl">
          SEARCHING{dot}
        </span>
      </div>
      <div className="flex flex-col">
        <LocationModal />
        <LocationCheckModal latitude={latitude} longitude={longitude} />
        <LocationWatchModal
          latitude={watchLatitude}
          longitude={watchLongitude}
        />
      </div>
    </div>
  );
};

export default LocationAlarm;
