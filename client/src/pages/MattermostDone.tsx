import DoneButton from "buttons/DoneButton";

const MattermostDone = () => {
  return (
    <div className="flex flex-col items-center">
        <div className="flex items-center my-40">
        <img className="w-10 h-10" src="../icons/Check.png" alt="" />
        <h1>인증 완료</h1>
        </div>
        <DoneButton title="튜토리얼 보기"/>
    </div>
  );
}

export default MattermostDone;