import { PlusCircledIcon } from '@radix-ui/react-icons';
import { IPickCreate, IPickInfo, IQuestion } from 'atoms/Pick.type';
import QuestionPlusModal from 'components/modals/QuestionPlusModal';
import WarningModal from 'components/modals/WarningModal';
import { Progress } from 'components/ui/progress';
import PassIcon from 'icons/PassIcon';
import WarningIcon from 'icons/WarningIcon';
import PlusIcon from 'icons/PlusIcon';

interface QuestionProps {
  question: IQuestion;
  pickInfo: IPickInfo;
  userPick: (data: IPickCreate) => void;
}

const Question = ({ question, pickInfo, userPick }: QuestionProps) => {
  const blockPassCount = pickInfo.blockCount + pickInfo.passCount;
  const pickBlockCount = pickInfo.pickCount + pickInfo.blockCount;

  return (
    <div
      className="text-white mx-4 rounded-lg p-3 pb-1"
      style={{ backgroundColor: '#000855', opacity: '80%' }}
    >
      <div className="flex flex-row justify-between items-center">
        <p className="px-2 py-1 text-xs bg-white rounded-lg text-color-000855">
          {question.category.name}
        </p>
      </div>
      <div className="m-4 flex flex-col justify-center">
        <div className="flex justify-center">
          <Progress value={pickBlockCount * 10} className="mb-4 w-4/5" />
        </div>
        <h1 className="text-center text-lg">{question.content}</h1>
        <div className="flex flex-row justify-center my-2">
          <img
            src={question.category.thumbnail}
            alt="categoryImg"
            className="w-[100px] h-[100px]"
          />
        </div>
        <div className="flex flex-row justify-around text-center mt-5">
          {blockPassCount < 5 && (
            <>
              <WarningModal
                question={question}
                userPick={userPick}
                title="block"
                blockPassCount={blockPassCount}
              />
              <WarningModal
                question={question}
                userPick={userPick}
                title="pass"
                blockPassCount={blockPassCount}
              />
            </>
          )}
          <QuestionPlusModal location="pickpage" />
        </div>
      </div>
    </div>
  );
};

export default Question;
