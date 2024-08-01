interface ProfileAlarmProps {
  title: string;
  content: string;
  children?: React.ReactNode;
}

const ProfileAlarm = ({ title, content, children }: ProfileAlarmProps) => {
  return (
    <div 
      className="bg-white rounded-lg flex justify-between items-center p-5 mx-4 my-2 h-24">
      <div>
        <div>{title}</div>  
        <div className="text-xs mt-1 text-gray-400">{content}</div>
      </div>
        {children}
    </div>
  )
}

export default ProfileAlarm;  