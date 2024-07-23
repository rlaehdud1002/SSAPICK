// import Question from 'components/PickPage/Question';
// import Choice from 'components/PickPage/Choice';
// import ShuffleIcon from 'icons/ShuffleIcon';
// import CoolTime from 'components/PickPage/CoolTime';
import PickComplete from "components/PickPage/PickComplete";

const Pick = () => {
  return (
    <div className='relative'>
      <PickComplete />
      {/* <CoolTime /> */}
      {/* <Question category="프로젝트" />
      <div className="m-7">
        <div className='flex flex-row justify-end'>
          <ShuffleIcon className="" />
        </div>
        <div className="flex flex-row justify-center">
          <Choice username="민준수" />
          <Choice username="이호영" />
        </div>
        <div className="flex flex-row justify-center">
          <Choice username="이인준" />
          <Choice username="황성민" />
        </div>
      </div> */}
    </div>
  );
};

export default Pick;
