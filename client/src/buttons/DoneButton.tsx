interface DoneButtonProps {
  title: string;
  width?: number;
  height?: number;
}

const DoneButton = ({ title }: DoneButtonProps) => {
  return (
    <div>
      <button
        type="submit"
        className="text-white background-color-5F86E9 font-medium rounded-lg text-sm mt-4 px-5 py-2.5 "
      >
        {title}
      </button>
    </div>
  );
};
export default DoneButton;
