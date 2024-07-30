import { DialogDescription } from '@radix-ui/react-dialog';
interface ResultCheckModalProps {
  content: string;
}


const ResultCheckModal = ({content}:ResultCheckModalProps) => {
  return <div>
    <DialogDescription className="flex justify-center">
      <h1 className="my-12">{content}</h1>
    </DialogDescription>
  </div>
}

export default ResultCheckModal;



