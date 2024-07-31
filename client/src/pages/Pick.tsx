import Question from 'components/PickPage/Question';
import Choice from 'components/PickPage/Choice';
import ShuffleIcon from 'icons/ShuffleIcon';
// import CoolTime from 'components/PickPage/CoolTime';
// import PickComplete from "components/PickPage/PickComplete";

const Pick = () => {
  return (
    <div className='relative'>
      {/* <PickComplete /> */}
      {/* <CoolTime /> */}
      <Question />
      <div className="m-7">
        <div className='flex flex-row justify-end'>
          <ShuffleIcon className="cursor-pointer" />
        </div>
        <div className="flex flex-row justify-center">
          <Choice username="민준수" gen="male" />
          <Choice username="이호영" gen="female" />
        </div>
        <div className="flex flex-row justify-center">
          <Choice username="이인준" gen="male" />
          <Choice username="황성민" gen="male" />
        </div>
      </div>
    </div>
  );
};

export default Pick;
