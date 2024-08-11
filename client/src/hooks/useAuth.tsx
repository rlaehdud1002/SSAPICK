import { useQuery } from '@tanstack/react-query';
import { mmAuthConfirm } from 'api/authApi';
import { isLoginState } from 'atoms/UserAtoms';
import Loading from 'components/common/Loading';
import { useState } from 'react';
import { useRecoilValue } from 'recoil';

export const useAuth = () => {
  const isLogin = useRecoilValue<boolean>(isLoginState);
  const [isMMState, setIsMMState] = useState<boolean>(false);
  const [isAuthInfoState, setIsAuthInfoState] = useState<boolean>(false);

  // mm 인증 확인
  const { data: isMM, isLoading: isMMLoading } = useQuery<boolean>({
    queryKey: ['isMM'],
    queryFn: mmAuthConfirm,
  });

  if (!isMMLoading && isMM) {
    setIsMMState(isMM);
  }

  if (isMMLoading) {
    return <Loading />;
  }

  return { isLogin, isMMState, isAuthInfoState };
};
