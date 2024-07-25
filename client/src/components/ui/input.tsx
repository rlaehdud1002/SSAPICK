import * as React from 'react';

import { cn } from 'lib/utils';
import { UseFormRegisterReturn } from 'react-hook-form';

export interface InputProps
  extends React.InputHTMLAttributes<HTMLInputElement> {
  register: UseFormRegisterReturn;
}

const Input = React.forwardRef<HTMLInputElement, InputProps>(
  ({ className, type, register }) => {
    return (
      <input
        type={type}
        className={cn(
          'text-color-000855 flex h-20 w-full rounded-lg border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground disabled:cursor-not-allowed disabled:opacity-50',
          className,
        )}
        {...register}
      />
    );
  },
);
Input.displayName = 'Input';

export { Input };

