import { SignOut } from "api/clientApi";
import WithdrawalModal from "components/modals/WithdrawalModal";

const SetAccount = () => {
  const handleLogout = async () => {
    await SignOut();
  };

  return (
    <div className="ml-4">
      <div className="text-xl mb-10 mt-3">계정 설정</div>
      <div className="text-sm ml-2" onClick={handleLogout} style={{ cursor: "pointer" }}>
        로그아웃
      </div>
      <div className="bg-white mr-5 my-3 h-px w-auto"></div>
      <WithdrawalModal title="회원탈퇴" />
      <div className="bg-white mr-5 my-3 h-px w-auto"></div>
    </div>
  );
};

export default SetAccount;
