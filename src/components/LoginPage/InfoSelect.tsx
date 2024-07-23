import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from 'components/ui/select';
import { UseFormRegisterReturn } from 'react-hook-form';

interface InfoSelectProps {
  title: string;
  register: UseFormRegisterReturn;
  setValue: any;
}

const InfoSelect = ({ title, register, setValue }: InfoSelectProps) => {
  const handleChange = (value: string) => {
    setValue(value);
  };

  console.log();

  return (
    <Select {...register} onValueChange={handleChange}>
      <SelectTrigger className="w-72 h-10 px-8 text-sm border-black">
        <SelectValue placeholder={title} />
      </SelectTrigger>
      <SelectContent>
        <SelectGroup >
          {title === "성별" && (
            <>
              <SelectItem value="남자" >남자</SelectItem>
              <SelectItem value="여자" >여자</SelectItem>
            </>
          )}
          {title === "기수" && (
            <>
              <SelectItem value="11">11기</SelectItem>
              <SelectItem value="12">12기</SelectItem>
            </>
          )}
          {title === "캠퍼스" && (
            <>
              <SelectItem value="서울">서울</SelectItem>
              <SelectItem value="대전">대전</SelectItem>
              <SelectItem value="광주">광주</SelectItem>
              <SelectItem value="부울경">부울경</SelectItem>
              <SelectItem value="구미">구미</SelectItem>
            </>
          )}
          {title === "반" && (
            <>
              <SelectItem value="1">1반</SelectItem>
              <SelectItem value="2">2반</SelectItem>
              <SelectItem value="3">3반</SelectItem>
              <SelectItem value="4">4반</SelectItem>
              <SelectItem value="5">5반</SelectItem>
              <SelectItem value="6">6반</SelectItem>
              <SelectItem value="7">7반</SelectItem>
              <SelectItem value="8">8반</SelectItem>
            </>
          )}
        </SelectGroup>
      </SelectContent>
    </Select>

  );
};

export default InfoSelect;


