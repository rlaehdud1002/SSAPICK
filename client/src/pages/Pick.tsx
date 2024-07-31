import Question from 'components/PickPage/Question';
import Choice from 'components/PickPage/Choice';
import ShuffleIcon from 'icons/ShuffleIcon';
// import CoolTime from 'components/PickPage/CoolTime';
// import PickComplete from "components/PickPage/PickComplete";

import { useRecoilValue } from 'recoil';
import { questionState } from 'atoms/PickAtoms';

const Pick = () => {
  const questions = useRecoilValue(questionState);
  return (
    <div className="relative">
      {/* <PickComplete /> */}
      {/* <CoolTime /> */}
      {questions.map((question, index) => {
        return (
          <div>
            <Question questionInfo={question} />;
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
