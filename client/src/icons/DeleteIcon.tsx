interface DeleteIconProps {
  className?: string;
}

const DeleteIcon = ({ className }: DeleteIconProps) => {
  return (
    <img
      src="/icons/TrashCan.png"
      alt=""
      className={className}
      width={25}
      height={25}
    />
  );
};

export default DeleteIcon;
