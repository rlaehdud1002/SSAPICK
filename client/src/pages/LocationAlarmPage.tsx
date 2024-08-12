import LocationCircle from 'components/LocationPage/LocationCircle';
import BackIcon from 'icons/BackIcon';

import { useQuery } from "@tanstack/react-query";
import { getUserInfo } from "api/authApi";
import { findFriends } from "api/locationApi";
import { IUserInfo } from "atoms/User.type";
import { userInfostate } from "atoms/UserAtoms";
import LocationImage from "components/LocationPage/LocationImage";
import { useLocation } from "hooks/useLocation";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useRecoilValue } from "recoil";

interface Position {
  t: number;
  l: number;
}

const LocationAlarm = () => {
  const [positionList] = useState<Position[]>([
    { t: 300, l: 50 },
    { t: 200, l: 300 },
    { t: 10, l: 180 },
    { t: 360, l: 200 },
    { t: 250, l: 150 },
    { t: 10, l: 300 },
    { t: 50, l: 100 },
    { t: 140, l: 340 },
    { t: 150, l: 50 },
    { t: 300, l: 300 },
  ])

  const userInfo = useRecoilValue(userInfostate)
  const nav = useNavigate();
  const [dot, setDot] = useState("");
  const { data: searchFriends, isLoading, refetch } = useQuery({
    queryKey: ["location"],
    queryFn: findFriends,
  });
  const { coords, error } = useLocation({
    refetch: refetch,
  });
  console.log(searchFriends?.data);

  // search text
  useEffect(() => {
    const interval = setInterval(() => {
      setDot((prevDots) => (prevDots === '...' ? '' : prevDots + '.'));
    }, 700);

    return () => clearInterval(interval); // 컴포넌트가 언마운트될 때 인터벌 정리
  }, []);

  const { data: information } = useQuery<IUserInfo>({
    queryKey: ['information'],
    queryFn: async () => await getUserInfo(),
  });

  return (
    <div>
      <div
        className="flex flex-row items-center m-2 cursor-pointer"
        onClick={() => nav(-1)}
      >
        <BackIcon />
      </div>
      <div className="relative flex ">
        <LocationCircle />
        <img className="absolute rounded-full w-16 h-16 top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2" src={information?.profileImage} alt="" />
        {searchFriends ? (
          [...searchFriends.data].map((friend: any, index: number) => (
            <div>
              <LocationImage
                key={index}
                top={positionList[index].t}
                left={positionList[index].l}
                profileImage={friend.profileImage}
              />
            </div>
          ))
        ) : (
          <span>
            <img src="" alt="" />
          </span>
        )
        }
      </div>
      <div className="text-center mt-9">
        <div className="flex flex-col font-bold space-y-3">
          <span className="text-[#3D6CE6] text-xl">
            현재 위치: {coords.latitude} / {coords.longitude}
          </span>
          <span className="luckiest_guy text-[#3D6CE6] text-3xl">
            SEARCHING{dot}
          </span>
        </div>
      </div>
    </div>
  );
};

export default LocationAlarm;
