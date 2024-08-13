interface ResultCheckModalProps {
  content: string;
  detail?: string;
}

const ResultCheckModal = ({ detail, content }: ResultCheckModalProps) => {
  return (
    <div className="flex flex-col ">
      <h1 className="flex justify-center my-10">{content}</h1>
      {detail ? <div className="flex justify-center text-sm mb-10">{detail}</div> : null}
    </div>
  );
};

export default ResultCheckModal;
