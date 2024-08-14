import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogTitle,
  DialogTrigger,
} from "components/ui/dialog";
import { useMutation } from "@tanstack/react-query";
import { signOut } from "api/authApi";
import ProfileAlarm from "components/ProfilePage/ProfileAlarm";
import { Button } from "components/ui/button";
import { DialogFooter, DialogHeader } from "components/ui/dialog";
import AccountIcon from "icons/AccountIcon";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import { accessTokenState } from "atoms/UserAtoms";

enum SignoutStep {
  CHECK,
  ALERT,
}

const SignoutModal = () => {
  const [step, setStep] = useState<SignoutStep>(SignoutStep.CHECK);
  const [open, setOpen] = useState<boolean>(false);
  const [accessToken, setAccessToken] = useRecoilState(accessTokenState);

  const nav = useNavigate();

  const mutation = useMutation({
    mutationKey: ['signout'],
    mutationFn: signOut,
    onSuccess: () => {
      if (step === SignoutStep.ALERT) {
        setTimeout(() => {
          setOpen(false);
        }, 1000);
      }
      setAccessToken("");
      nav("/");
    },
  });

  return (
    <Dialog open={open} onOpenChange={(open) => setOpen(open)}>
      <DialogTrigger
        className="w-full"
        onClick={() => {
          setStep(SignoutStep.CHECK);
          setOpen(true);
        }}
      >
        <ProfileAlarm title="로그아웃">
          <AccountIcon width={50} height={50} />
        </ProfileAlarm>
      </DialogTrigger>

      <DialogContent className="mx-2 w-4/5 rounded-lg">
        <DialogHeader className="flex items-start text-[#4D5BDC]">
          <DialogTitle>로그아웃</DialogTitle>
        </DialogHeader>

        {step === SignoutStep.CHECK && (
          <div>
            <div className="flex justify-center mt-5">
              <DialogDescription>로그아웃을 하시겠습니까?</DialogDescription>
            </div>
            <DialogFooter className="flex items-end mt-5">
              <Button
                onClick={() => {
                  mutation.mutate();
                  setStep(SignoutStep.ALERT);
                }}
                variant="ssapick"
                size="sm"
              >
                로그아웃
              </Button>
            </DialogFooter>
          </div>
        )}

        {step === SignoutStep.ALERT && (
          <div>
            <div className="flex justify-center my-5">
              <DialogDescription>로그아웃 완료</DialogDescription>
            </div>
          </div>
        )}
      </DialogContent>
    </Dialog>
  );
};

export default SignoutModal;
