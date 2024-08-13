interface NoMessageProps {
  content: string;
}

const NoMessage = ({ content }: NoMessageProps) => {
  return (
    <div>
      <h1 className="text-center py-3">{content}</h1>
    </div>
  );
};

export default NoMessage;
