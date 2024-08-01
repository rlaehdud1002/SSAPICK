import QuestionPlusModal from 'components/modals/QuestionPlusModal';
import WarningModal from 'components/modals/WarningModal';
import PassIcon from 'icons/PassIcon';
import QuestionImageIcon from 'icons/QuestionIcon';

import { IQuestion } from 'atoms/Pick.type';

interface QuestionProps {
  questionInfo: IQuestion;
}

const Question = ({ questionInfo }: QuestionProps) => {
  return (
    <div
      className="text-white mx-4 rounded-lg p-3 pb-1"
      style={{ backgroundColor: '#000855', opacity: '80%' }}
    >
      <div className="flex flex-row justify-between items-center">
        <p className="px-2 py-1 text-xs bg-white rounded-xl text-color-000855">
          {questionInfo.category.name}
        </p>
        <QuestionPlusModal />
      </div>
      <div className="m-4 flex flex-col justify-center">
        <p className="text-xs text-right text-red-400">2 of 10</p>
        <h1 className="text-center text-lg">{questionInfo.content}</h1>
        <div className="flex flex-row justify-end mt-1">
          <WarningModal question={questionInfo.content} />
          <PassIcon />
        </div>
        <div className="flex flex-row justify-center">
          <QuestionImageIcon width={150} height={150} />
        </div>
      </div>
    </div>
  );
};

export default Question;
