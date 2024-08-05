import { useMutation } from "@tanstack/react-query";
import { signOut } from "api/authApi";
import { accessTokenState } from "atoms/UserAtoms";
import WithdrawalModal from "components/modals/WithdrawalModal";
import { useNavigate } from "react-router-dom";
import { useSetRecoilState } from "recoil";

const SetAccount = () => {
  const navigate = useNavigate();
  const setAccessToken = useSetRecoilState(accessTokenState);

  const mutation = useMutation({
    mutationFn: signOut,
    onSuccess: () => {
      console.log("로그아웃 성공");
      setAccessToken("");
      navigate("/");
    }
  });

  const handleLogout = async () => {
    mutation.mutate();
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
