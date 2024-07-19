import PlusIcon from 'icons/PlusIcon';
import QuestionImageIcon from 'icons/QuestionIcon';

interface QuestionProps {
  category: string;
}

const Question = ({ category }: QuestionProps) => {
  return (
    <div
      className="text-white mx-4 rounded-md p-3"
      style={{ backgroundColor: '#000855', opacity: '80%' }}
    >
      <div className="flex flex-row justify-between">
        <p className="px-2 py-1 text-xs bg-white rounded-xl text-color-000855">
          프로젝트
        </p>
        <PlusIcon />
      </div>
      <div className='m-4 flex flex-col items-center'>
        <p className="text-xs text-right">2 of 10</p>
        <h1 className="text-center text-xl py-5">프로젝트 같이 하고 싶은 사람</h1>
        <QuestionImageIcon width={200} height={200}/>
      </div>
    </div>
  );
};

export default Question;
