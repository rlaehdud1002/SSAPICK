import { Switch } from 'components/ui/switch';

interface SetAlarmContentProps {
  title: string;
  content: string;
  children: React.ReactNode;
  checked?: boolean;
  onCheckedChange?: (checked: boolean) => void;
}

const SetAlarmContent = ({
  title,
  checked,
  onCheckedChange,
  content,
  children,
}: SetAlarmContentProps) => {
  return (
    <div className="mb-5">
      <div className="bg-white/50 rounded-lg flex flex-row justify-between items-center p-4">
        <div className="flex flex-row items-center">
          {children}
          <span className="mx-6">{title}</span>
        </div>
        <Switch checked={checked} onCheckedChange={onCheckedChange} />
      </div>
      <span className="text-xs text-gray-600 ms-2">{content}</span>
    </div>
  );
};

export default SetAlarmContent;
