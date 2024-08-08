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
import { useQuery } from '@tanstack/react-query';
import { getCategory } from 'api/questionApi';

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
    setValue(parseInt(value));
  };

  const { data: categorys, isLoading: categoryLoading } = useQuery({
    queryKey: ['categorys'],
    queryFn: getCategory,
  });

  return (
    <div className='my-3'>
      <div>
        <Select {...register} onValueChange={handleChange}>
          <SelectTrigger className="w-32 h-7 border-[#7EAFFF]">
            <SelectValue placeholder={title} />
          </SelectTrigger>
          <SelectContent>
            <SelectGroup>
              {categorys && categorys.map((category, index) => {
                return (
                  <SelectItem key={index} value={String(category.id)}>
                    {category.name}
                  </SelectItem>
                );
              })}
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
