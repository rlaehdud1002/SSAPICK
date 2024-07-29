import { Switch } from 'components/ui/switch';

interface SetAlarmContentProps {
  title: string;
  content: string;
  children: React.ReactNode;
}

const SetAlarmContent = ({
  title,
  content,
  children,
}: SetAlarmContentProps) => {
  return (
    <div className='mb-5'>
      <div className="bg-white/50 rounded-xl flex flex-row justify-between items-center p-4">
        <div className="flex flex-row items-center">
          {children}
          <span className="mx-6">{title}</span>
        </div>
        <Switch />
      </div>
      <span className="text-xs text-gray-600 ms-2">{content}</span>
    </div>
  );
};

export default SetAlarmContent;
