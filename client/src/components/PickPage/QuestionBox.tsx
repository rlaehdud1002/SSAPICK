import { IPickCreate, IPickInfo, IQuestion } from 'atoms/Pick.type';
import QuestionPlusModal from 'components/modals/QuestionPlusModal';
import WarningModal from 'components/modals/WarningModal';
import PassIcon from 'icons/PassIcon';
import { Progress } from 'components/ui/progress';

interface QuestionProps {
  question: IQuestion;
  pickInfo: IPickInfo;
  userPick: (data: IPickCreate) => void;
}

const Question = ({ question, pickInfo, userPick }: QuestionProps) => {
  const blockPassCount = pickInfo.blockCount + pickInfo.passCount;
  console.log('blockPassCount', blockPassCount);

  return (
    <div
      className="text-white mx-4 rounded-lg p-3 pb-1"
      style={{ backgroundColor: '#000855', opacity: '80%' }}
    >
      <div className="flex flex-row justify-between items-center">
        <p className="px-2 py-1 text-xs bg-white rounded-lg text-color-000855">
          {question.category.name}
        </p>
        <QuestionPlusModal />
      </div>
      <div className="m-4 flex flex-col justify-center">
        <div className="flex justify-center">
          <Progress
            value={(pickInfo.pickCount + pickInfo.blockCount) * 10}
            className="mb-4 w-4/5"
          />
        </div>
        <h1 className="text-center text-lg">{question.content}</h1>
        {blockPassCount < 5 && (
          <div className="flex flex-row justify-end mt-1">
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
          </div>
        )}
        <div className="flex flex-row justify-center">
          <img
            src={question.category.thumbnail}
            alt="categoryImg"
            width={100}
            height={100}
          />
        </div>
      </div>
    </div>
  );
};

export default Question;
