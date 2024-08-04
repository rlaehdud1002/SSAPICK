import { QueryClient, useMutation, useQuery } from '@tanstack/react-query';
import { mmAuthConfirm, mmAuthSend } from 'api/authApi';
import DoneButton from 'buttons/DoneButton';
import { useForm } from 'react-hook-form';
import AuthInput from '../components/MattermostPage/AuthInput';
import MattermostIcon from '../icons/MattermostIcon';
import { useNavigate } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';
import { useEffect } from 'react';

interface AuthFormm {
  id: string;
  password: string;
}

const Mattermost = () => {
  const { data: authenticated, isLoading } = useQuery<boolean>({
    queryKey: ['authenticated'],
    queryFn: async () => await mmAuthConfirm(),
  });

  const navigate = useNavigate();

  const mutation = useMutation({
    mutationKey: ['auth', 'send'],
    mutationFn: mmAuthSend,
    // 성공시, 유저 정보 입력 페이지로 이동
    onSuccess: () => {
      navigate('/login/userinfo');
      console.log('성공');
    },
    // 실패시,
    // onError: () => {
    //   // console.log(message);
    // }
  });

  useEffect(() => {
    if (authenticated) {
      navigate('/login/userinfo');
    }
  }, [authenticated, navigate]);

  const { register, handleSubmit } = useForm<AuthFormm>();

  const onSubmit = (data: AuthFormm) => {
    mutation.mutate({
      loginId: data.id,
      password: data.password,
    });
  };

  const onInvalid = (errors: any) => {
    console.log(errors);
  };
  return (
    <form onSubmit={handleSubmit(onSubmit, onInvalid)}>
      <div className="flex flex-col items-center mt-16">
        <h1 className="my-5">Mattermost 인증</h1>
        <MattermostIcon />
        <span className="text-xs mt-2 mb-20">
          본 인증은 <span className="luckiest_guy">ssapick</span> 서비스 이용을
          위한 필수 사항입니다.{' '}
        </span>
        {/* 에러시, 메세지 */}
        {/* <span className="text-xs mt-3 text-red-500">
            아이디 또는 비밀번호가 일치하지 않습니다.
          </span> */}
        <AuthInput
          title="매터모스트 아이디"
          type="text"
          placeholder="아이디를 입력해주세요."
          register={register('id', {
            required: '매터모스트 아이디를 입력해주세요.',
          })}
        />
        <AuthInput
          title="매터모스트 비밀번호"
          type="password"
          placeholder="비밀번호를 입력해주세요."
          register={register('password', {
            required: '매터모스트 비밀번호를 입력해주세요.',
          })}
        />
        <span className="text-xs">
          입력하신 인증정보는 오직 인증을 목적으로만 사용됩니다.
        </span>
        <div className="mt-10">
          <DoneButton title="인증하기" />
        </div>
      </div>
    </form>
  );
};

export default Mattermost;
