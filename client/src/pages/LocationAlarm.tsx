import BackIcon from 'icons/BackIcon';

import { useNavigate } from 'react-router-dom';

const LocationAlarm = () => {
  const nav = useNavigate();
  
  return (
    <div>
      <div
        className="flex flex-row items-center m-2 cursor-pointer"
        onClick={() => nav(-1)}
      >
        <BackIcon />
      </div>
    </div>
  );
};

export default LocationAlarm;
