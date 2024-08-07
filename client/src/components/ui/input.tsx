import * as React from 'react';

import SearchIcon from 'icons/SearchIcon';
import { cn } from 'lib/utils';
import { UseFormRegisterReturn } from 'react-hook-form';

export interface InputProps
  extends React.InputHTMLAttributes<HTMLInputElement> {
  register: UseFormRegisterReturn;
  search?: boolean;
}

const Input = React.forwardRef<HTMLInputElement, InputProps>(
  ({ className, type, register, search, ...props }, ref) => {
    return (
      <div className="w-full relative">
        {search && (
          <button type="submit" className="absolute right-2 bottom-2">
            <SearchIcon width={8} height={8} />
          </button>
        )}
        <input
          type={type}
          autoComplete="off"
          className={cn(
            'text-color-000855 flex rounded-lg border border-input bg-transparent px-3 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground disabled:cursor-not-allowed disabled:opacity-50 pr-10 ',
            className,
          )}
          {...register}
          {...props}
          {...ref}
        />
      </div>
    );
  },
);
Input.displayName = 'Input';

export { Input };
