import { DropdownMenuRadioItem } from 'components/ui/dropdown-menu';
interface DropContentProps {
  content: string;
}

const DropContent = ({ content }: DropContentProps) => {
  return (
    <div>
      <DropdownMenuRadioItem value="content">{content}</DropdownMenuRadioItem>
    </div>
  );
};
export default DropContent;
