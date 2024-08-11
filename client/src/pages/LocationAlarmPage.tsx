import BackIcon from 'icons/BackIcon';
import UserPickIcon from 'icons/UserPickIcon';
import LocationCircle from 'components/LocationPage/LocationCircle';

import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import LocationModal from "components/modals/LocationModal";
import { useLocation } from "hooks/useLocation";
import { useQuery } from "@tanstack/react-query";
import { findFriends } from "api/locationApi";
import { useRecoilState, useRecoilValue } from "recoil";
import { userInfostate } from "atoms/UserAtoms";
import { IUserInfo } from "atoms/User.type";
import { getUserInfo } from "api/authApi";
import LocationImage from "components/LocationPage/LocationImage";


const LocationAlarm = () => {
  const userInfo = useRecoilValue(userInfostate)
  const nav = useNavigate();
  const [dot, setDot] = useState("");
  const { data:searchFriends, isLoading, refetch } = useQuery({
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
  console.log("search", searchFriends)

  return (
    <div>
      <div
        className="flex flex-row items-center m-2 cursor-pointer"
        onClick={() => nav(-1)}
      >
        <BackIcon />
      </div>
      <div className="relative flex justify-center items-center">
          <LocationCircle />
          <img className="absolute rounded-full w-16 h-16 top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2" src={information?.profileImage} alt="" />
          {/* {searchFriends? (
            searchFriends.data.map((friend:any, index:number) => (
              <div>
                <img 
                className="rounded-full w-16 h-16 absolute top- left-48" 
                src={friend.profileImage} alt="" />
              { <LocationImage 
              key={index} 
              top={0}
              left={0}
              // left={parseFloat(friend.position.x.toFixed(0))}
              // top={parseFloat(friend.position.y.toFixed(0))}
              profileImage={friend.profileImage}
              /> }
              </div>
              ))
          ):(
            <span>
              <img src="" alt="" />
            </span>
          )
        } */}
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
