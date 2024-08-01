import Question from 'components/PickPage/QuestionBox';
import Choice from 'components/PickPage/ChoiceBox';
import ShuffleIcon from 'icons/ShuffleIcon';
import { useQuery } from '@tanstack/react-query';
import { IQuestion } from 'atoms/Pick.type';
import { getQuestion } from 'api/questionApi';
// import CoolTime from 'components/PickPage/CoolTime';
// import PickComplete from "components/PickPage/PickComplete";

const Pick = () => {
  const { data: questions, isLoading } = useQuery<IQuestion[]>({
    queryKey: ['questions'],
    queryFn: getQuestion,
  });

  // ! FIXED : questions가 빈 배열은 ok인데 왜 undefined일까? 
  console.log('questions', questions, isLoading);

  return (
    <div className="relative">
      {/* <PickComplete /> */}
      {/* <CoolTime /> */}
      {questions &&
        questions.map((question, index) => {
          return (
            <div>
              <Question question={question} />
              <div className="m-7">
                <div className="flex flex-row justify-end">
                  <ShuffleIcon className="cursor-pointer" />
                </div>
                <div className="flex flex-row justify-center">
                  <Choice username="민준수" gen="M" />
                  <Choice username="이호영" gen="F" />
                </div>
                <div className="flex flex-row justify-center">
                  <Choice username="이인준" gen="M" />
                  <Choice username="황성민" gen="M" />
                </div>
              </div>
            </div>
          );
        })}
    </div>
  );
};

export default Pick;
