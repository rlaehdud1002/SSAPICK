interface ResultCheckModalProps {
  content: string;
}

const ResultCheckModal = ({ content }: ResultCheckModalProps) => {
  return (
    <div className="flex justify-center">
      <h1 className="my-12">{content}</h1>
    </div>
  );
};

export default ResultCheckModal;
