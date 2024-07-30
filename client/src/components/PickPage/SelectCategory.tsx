import { ErrorMessage } from '@hookform/error-message';
import { UseFormRegisterReturn } from 'react-hook-form';

import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from 'components/ui/select';

interface SelectCategoryProps {
  title: string;
  register: UseFormRegisterReturn;
  setValue: any;
  errors?: object;
  name: string;
}

const SelectCategory = ({
  title,
  register,
  setValue,
  errors,
  name,
}: SelectCategoryProps) => {
  const handleChange = (value: string) => {
    setValue(value);
  };

  return (
    <div>
      <div>
        <Select {...register} onValueChange={handleChange}>
          <SelectTrigger className="w-32 h-7 border-[#7EAFFF]">
            <SelectValue placeholder={title} />
          </SelectTrigger>
          <SelectContent>
            <SelectGroup>
              <SelectItem value="연애/데이트">연애/데이트</SelectItem>
              <SelectItem value="친구">친구</SelectItem>
              <SelectItem value="가족">가족</SelectItem>
              <SelectItem value="학업/프로젝트">학업/프로젝트</SelectItem>
              <SelectItem value="직장/커리어">직장/커리어</SelectItem>
              <SelectItem value="건강/운동">건강/운동</SelectItem>
              <SelectItem value="취미/여가">취미/여가</SelectItem>
              <SelectItem value="기술/IT">기술/IT</SelectItem>
              <SelectItem value="문화/예술">문화/예술</SelectItem>
              <SelectItem value="패션/뷰티">패션/뷰티</SelectItem>
              <SelectItem value="라이프스타일">라이프스타일</SelectItem>
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

export default SelectCategory;
