import LocationCircle from "components/LocationPage/LocationCircle";
import BackIcon from "icons/BackIcon";

import { useQuery } from "@tanstack/react-query";
import { findFriends } from "api/locationApi";
import { userInfostate } from "atoms/UserAtoms";
import { useLocation } from "hooks/useLocation";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useRecoilValue } from "recoil";

const LocationAlarm = () => {
  const userInfo = useRecoilValue(userInfostate);
  const nav = useNavigate();
  const [dot, setDot] = useState("");
  const { data: locations, isLoading, refetch } = useQuery({
    queryKey: ["location"],
    queryFn: findFriends,
  });
  const { coords, error } = useLocation({
    refetch: refetch,
  });
  {
    locations &&
    console.log(locations.data);
  }

  // search text
  useEffect(() => {
    const interval = setInterval(() => {
      setDot((prevDots) => (prevDots === "..." ? "" : prevDots + "."));
    }, 700);

    return () => clearInterval(interval); // 컴포넌트가 언마운트될 때 인터벌 정리
  }, []);

  return (
    <div>
      <div className="flex flex-row items-center m-2 cursor-pointer" onClick={() => nav(-1)}>
        <BackIcon />
      </div>
      <div className="relative flex justify-center items-center">
        <div className="relative">
          <LocationCircle />
          <img src={userInfo.profileImage} className="absolute w-20 h-20 top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 rounded-full" alt="profileImpge" />
          {locations && locations.data.map((location: any, index: number) => (
              <img key={index} className="rounded-full w-20 h-20" src={location.profileImage} alt="" />
          ))}
        </div>
      </div>
      <div className="text-center mt-9">
        <div className="flex flex-col font-bold space-y-3">
          <span className="text-[#3D6CE6] text-xl">
            현재 위치: {coords.latitude} / {coords.longitude}
          </span>
          <span className="luckiest_guy text-[#3D6CE6] text-3xl">SEARCHING{dot}</span>
        </div>
      </div>
    </div>
  );
};

export default LocationAlarm;
