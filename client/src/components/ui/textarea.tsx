import * as React from 'react';

import { cn } from 'lib/utils';
import { UseFormRegisterReturn } from 'react-hook-form';

export interface TextareaProps
  extends React.TextareaHTMLAttributes<HTMLTextAreaElement> {
  register: UseFormRegisterReturn;
}

const Textarea = React.forwardRef<HTMLTextAreaElement, TextareaProps>(
  ({ className, register, ...props }, ref) => {
    return (
      <textarea
        className={cn(
          'border-none input-box flex h-[100px] w-full rounded-md px-3 py-2 text-sm placeholder:text-muted-foreground focus:outline-none disabled:cursor-not-allowed disabled:opacity-50',
          'resize-none',
          className,
        )}
        {...register}
        {...props}
        {...ref}
      />
    );
  },
);
Textarea.displayName = 'Textarea';

export { Textarea };
