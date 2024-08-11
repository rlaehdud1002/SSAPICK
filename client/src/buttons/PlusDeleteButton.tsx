interface PlusDeleteButtonProps {
  title: string;
  isDelete?: boolean;
} 

const PlusDeleteButton = ({title,isDelete}:PlusDeleteButtonProps) => {
  
  return <div>
    {isDelete ?(<button type="submit" className="text-white bg-blue-300 font-medium rounded-lg text-xs py-1 px-2">{title}</button>)
    :(<button type="submit" className="text-white bg-slate-400 font-medium rounded-lg text-xs py-1 px-2 ">{title}</button>)}
    </div>
}
export default PlusDeleteButton;