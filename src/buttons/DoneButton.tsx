interface DoneButtonProps {
  title: string;

}

const DoneButton = ({title}:DoneButtonProps) => (
  <button type="submit" className="text-white bg-blue-500 font-medium rounded-lg text-sm my-10 px-5 py-2.5 " >{title}</button>
);

export default DoneButton;
