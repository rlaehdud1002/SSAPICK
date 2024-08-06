interface NoMessageProps {
  content: string;
}

const NoMessage = ({ content }: NoMessageProps) => {
  return (
    <div>
      <h1 className="text-center text-xl py-5">{content}</h1>
    </div>
  );
};

export default NoMessage;
