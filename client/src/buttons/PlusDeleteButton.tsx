interface PlusDeleteButtonProps {
  title: string;
  isDlete?: boolean;
} 

const PlusDeleteButton = ({title,isDlete}:PlusDeleteButtonProps) => {
  
  return <div>
    {isDlete ?(<button type="submit" className="text-white background-color-5F86E9 font-medium rounded-lg text-xs py-1 px-2">{title}</button>)
    :(<button type="submit" className="text-white bg-slate-400 font-medium rounded-lg text-xs py-1 px-2 ">{title}</button>)}
    </div>
}
export default PlusDeleteButton;