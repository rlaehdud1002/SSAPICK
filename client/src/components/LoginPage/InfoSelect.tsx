import { ErrorMessage } from '@hookform/error-message';
import { Label } from 'components/ui/label';
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
  errors?: object;
  name: string;
  setValue: (value: any) => void;
  defaultValue?: any;
  disabled?: boolean;
}

const InfoSelect = ({
  title,
  register,
  setValue,
  errors,
  name,
  defaultValue,
  disabled,
}: InfoSelectProps) => {
  const handleChange = (value: string) => {
    setValue(value);
  };

  return (
    <div>
      <div>
        <Select
          defaultValue={String(defaultValue)}
          {...register}
          onValueChange={handleChange}
          disabled={disabled}
        >
          <SelectTrigger
            className="w-72 h-10 px-8 text-sm border-black"
            disabled={disabled}
          >
            <Label className="w-16 text-start" htmlFor={title}>
              {title}
            </Label>
            <SelectValue />
          </SelectTrigger>
          <SelectContent>
            <SelectGroup>
              {title === '성별' && (
                <>
                  <SelectItem value="M">남자</SelectItem>
                  <SelectItem value="F">여자</SelectItem>
                </>
              )}
              {title === '기수' && (
                <>
                  <SelectItem value="11">11기</SelectItem>
                  <SelectItem value="12">12기</SelectItem>
                </>
              )}
              {title === '캠퍼스' && (
                <>
                  <SelectItem value="서울">서울</SelectItem>
                  <SelectItem value="대전">대전</SelectItem>
                  <SelectItem value="광주">광주</SelectItem>
                  <SelectItem value="부울경">부울경</SelectItem>
                  <SelectItem value="구미">구미</SelectItem>
                </>
              )}
              {title === '반' && (
                <>
                  <SelectItem value="1">1반</SelectItem>
                  <SelectItem value="2">2반</SelectItem>
                  <SelectItem value="3">3반</SelectItem>
                  <SelectItem value="4">4반</SelectItem>
                  <SelectItem value="5">5반</SelectItem>
                  <SelectItem value="6">6반</SelectItem>
                  <SelectItem value="7">7반</SelectItem>
                  <SelectItem value="8">8반</SelectItem>
                  <SelectItem value="9">9반</SelectItem>
                  <SelectItem value="10">10반</SelectItem>
                  <SelectItem value="11">11반</SelectItem>
                  <SelectItem value="12">12반</SelectItem>
                  <SelectItem value="13">13반</SelectItem>
                  <SelectItem value="14">14반</SelectItem>
                  <SelectItem value="15">15반</SelectItem>
                  <SelectItem value="16">16반</SelectItem>
                  <SelectItem value="17">17반</SelectItem>
                  <SelectItem value="18">18반</SelectItem>
                  <SelectItem value="19">19반</SelectItem>
                  <SelectItem value="20">20반</SelectItem>
                  <SelectItem value="21">21반</SelectItem>
                  <SelectItem value="22">22반</SelectItem>
                </>
              )}
            </SelectGroup>
          </SelectContent>
        </Select>
      </div>
      <div className="mt-1 ml-2">
        <ErrorMessage
          errors={errors}
          name={name}
          render={({ message }) => (
            <h6 className="text-red-400 text-xs ">{message}</h6>
          )}
        />
      </div>
    </div>
  );
};

export default InfoSelect;
