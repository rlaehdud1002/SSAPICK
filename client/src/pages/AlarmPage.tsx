import AlarmIcon from "icons/AlarmIcon";
import BackIcon from "icons/BackIcon";
import AlarmContent from "components/AlarmPage/AlarmContent";

import { useNavigate } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import { INotification } from "atoms/Notification.type";
import { getNotificationList } from "api/notificationApi";

const Alarm = () => {
  const nav = useNavigate();

  const { data: notifications, isLoading } = useQuery<INotification[]>({
    queryKey: ["notification"],
    queryFn: getNotificationList,
  });

  if (isLoading) return <div>로딩중</div>;

  return (
    <div>
      <div className="flex flex-row items-center m-2 cursor-pointer" onClick={() => nav(-1)}>
        <BackIcon />
        <AlarmIcon />
        {/* <h1>알림</h1> */}
      </div>
      <div className="m-6">
        {notifications?.map((notification, index) => (
          <AlarmContent
            key={index}
            category={notification.type}
            content={notification.message}
            read={notification.read}
          />
        ))}
      </div>
    </div>
  );
};

export default Alarm;
