import LocationCircle from "components/LocationPage/LocationCircle";
import BackIcon from "icons/BackIcon";

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
import { ILocation } from "atoms/Location.type";
import LocationModal from "components/modals/LocationModal";

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
  ]);

  const nav = useNavigate();
  const [dot, setDot] = useState("");
  const {
    data: searchFriends,
    isLoading,
    refetch,
  } = useQuery<ILocation>({
    queryKey: ["location"],
    queryFn: findFriends,
  });

  console.log(searchFriends);

  const { coords, error } = useLocation({
    refetch: refetch,
  });

  // search text
  useEffect(() => {
    const interval = setInterval(() => {
      setDot((prevDots) => (prevDots === "..." ? "" : prevDots + "."));
    }, 700);

    return () => clearInterval(interval); // 컴포넌트가 언마운트될 때 인터벌 정리
  }, []);

  const { data: information } = useQuery<IUserInfo>({
    queryKey: ["information"],
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
      <div className="flex justify-center">
      <span className="text-[#3D6CE6] mb-5">최대 10명의 친구를 찾아보세요!</span>
      </div>
      <div className="relative flex ">
        <LocationCircle />
        <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2">
          <div className="w-16 h-16 bg-blue-400 rounded-full flex justify-center items-center">
            <img
              className="w-14 h-14 rounded-full"
              src={information?.profileImage}
              alt=""
            />
          </div>
        </div>
        {searchFriends ? (
          [...searchFriends.locations].map((friend: any, index: number) => (
            <div>
              <LocationImage
                key={index}
                top={positionList[index].t}
                left={positionList[index].l}
                profileImage={friend.profileImage}
                username={friend.username}
              />
            </div>
          ))
        ) : (
          <span>
            <img src="" alt="" />
          </span>
        )}
      </div>
      <div className="text-center mt-9">
        <div className="flex flex-col space-y-3">
            <span className="luckiest_guy text-[#3D6CE6] text-md">
              현재까지 {searchFriends?.count}명의 친구를 찾았습니다<br/>
            </span>
      
            <span className="luckiest_guy text-[#3D6CE6] text-lg mt-10">
              내 주변 친구 찾는 중{dot}
            </span>
        
        </div>
      </div>
    </div>
  );
};

export default LocationAlarm;
