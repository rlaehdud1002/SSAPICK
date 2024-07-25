import { Link } from "react-router-dom";
import WithdrawalModal from "components/modals/WithdrawalModal";


const SetAccount = () => {

  return <div className="ml-4">
    <div className="text-xl mb-10 mt-3">계정 설정</div>
    <Link to="/home">
    <div className="text-sm ml-2">로그아웃</div>
    </Link>
    <div className="bg-white mr-5 my-3 h-px w-auto"></div>
    <WithdrawalModal title="회원탈퇴"/>
    <div className="bg-white mr-5 my-3 h-px w-auto"></div>
  </div>
 
}

export default SetAccount;