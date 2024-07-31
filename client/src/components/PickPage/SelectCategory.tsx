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
              <SelectItem value="1">연애/데이트</SelectItem>
              <SelectItem value="2">친구</SelectItem>
              <SelectItem value="3">가족</SelectItem>
              <SelectItem value="4">학업/프로젝트</SelectItem>
              <SelectItem value="5">직장/커리어</SelectItem>
              <SelectItem value="6">건강/운동</SelectItem>
              <SelectItem value="7">취미/여가</SelectItem>
              <SelectItem value="8">기술/IT</SelectItem>
              <SelectItem value="9">문화/예술</SelectItem>
              <SelectItem value="10">패션/뷰티</SelectItem>
              <SelectItem value="11">라이프스타일</SelectItem>
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
