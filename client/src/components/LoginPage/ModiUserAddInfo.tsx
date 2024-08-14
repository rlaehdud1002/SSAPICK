import { useMutation, useQuery } from '@tanstack/react-query';
import { UserSend, getUserInfo } from 'api/authApi';
import { pickFriendState } from 'atoms/FriendAtoms';
import { IUserInfo } from 'atoms/User.type';
import { profileImageState, sendUserInfoState } from 'atoms/UserAtoms';
import DoneButton from 'buttons/DoneButton';
import InfoInput from 'components/LoginPage/InfoInput';
import Loading from 'components/common/Loading';
import BackIcon from 'icons/BackIcon';
import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import { useRecoilState, useRecoilValue } from 'recoil';

interface AddUserForm {
  mbti: string;
  major: string;
  birth: string;
  town: string;
  hobby: string;
}

const ModiUserAddInfo = () => {
  const navigate = useNavigate();
  const [SendUserInfo, setSendUserInfo] = useRecoilState(sendUserInfoState);
  const [pickFriends, setPickFriends] = useRecoilState(pickFriendState);
  const profileImage = useRecoilValue(profileImageState);

  // 유저 정보 조회
  const { data: information, isLoading } = useQuery<IUserInfo>({
    queryKey: ['information'],
    queryFn: async () => await getUserInfo(),
  });

  const mutation = useMutation({
    mutationKey: ['user', 'send'],
    mutationFn: UserSend,

    onSuccess: () => {
      navigate('/profile');
      setSendUserInfo((prev) => {
        return {
          ...prev,
          mbti: undefined,
          major: undefined,
          birth: undefined,
          interest: undefined,
          residentialArea: undefined,
        };
      });
      console.log('성공');
      setPickFriends([]);
    },
  });

  const {
    register,
    reset,
    handleSubmit,
    formState: { errors },
  } = useForm<AddUserForm>();

  console.log(SendUserInfo, profileImage);

  const onSubmit = (data: AddUserForm) => {
    // 유저 정보 저장
    setSendUserInfo((prev) => {
      return {
        ...prev,
        mbti: data.mbti,
        major: data.major,
        birth: data.birth,
        interest: data.hobby,
        residentialArea: data.town,
      };
    });
  };

  const onInvalid = (errors: any) => {
    console.log(errors);
  };

  useEffect(() => {
    if (SendUserInfo.mbti) {
      const form = new FormData();
      if (profileImage) {
        form.append('profileImage', profileImage);
      }
      form.append(
        'update',
        new Blob([JSON.stringify(SendUserInfo)], { type: 'application/json' }),
      );

      mutation.mutate(form);
    }
  }, [SendUserInfo]);

  useEffect(() => {
    if (!isLoading && information) {
      reset({
        mbti: information.hints[5].content || '',
        major: information.hints[6].content || '',
        birth: information.hints[7].content || '',
        town: information.hints[8].content || '',
        hobby: information.hints[9].content || '',
      });
    }
  }, [isLoading, information, reset]);

  const goToBack = () => {
    window.location.reload();
  };

  if (isLoading) {
    return <Loading />;
  }

  return (
    <form onSubmit={handleSubmit(onSubmit, onInvalid)}>
      <div className="flex items-center ml-4 cursor-pointer" onClick={goToBack}>
        <BackIcon />
        <span className="ml-2">추가 정보 수정</span>
      </div>
      <div className="flex flex-col my-5">
        <div className="ml-10">
          <div style={{ fontSize: 11 }}>추후 랜덤 힌트로 사용됩니다.</div>
          <div className="mb-20" style={{ color: 'red', fontSize: 10 }}>
            모든 정보 입력이 필수입니다.
          </div>
        </div>
        <div className="flex w-full flex-col justify-center items-center space-y-2">
          <InfoInput
            name="mbti"
            title="MBTI"
            register={register('mbti', {
              required: 'MBTI를 입력해주세요.',
              maxLength: { value: 4, message: '4글자로 입력해주세요.' },
              minLength: { value: 4, message: '4글자로 입력해주세요.' },
              pattern: {
                value: /^[E,I,N,S,T,F,P,J]{4}$/,
                message: '대문자 MBTI 유형으로 입력해주세요.',
              },
            })}
            errors={errors}
          />

          <InfoInput
            name="major"
            title="전공"
            register={register('major', {
              required: '전공을 입력해주세요.',
              maxLength: { value: 20, message: '20글자 이하로 입력해주세요.' },
            })}
            errors={errors}
          />
          <InfoInput
            name="birth"
            title="생년월일"
            register={register('birth', {
              required: '생년월일을 입력해주세요.',
              pattern: {
                value: /^\d{4}-\d{2}-\d{2}$/,
                message: 'YYYY-MM-DD 형식으로 입력해주세요.',
              },
            })}
            errors={errors}
          />
          <InfoInput
            name="town"
            title="동네 "
            register={register('town', {
              required: '동네를 입력해주세요.',
              maxLength: { value: 20, message: '20글자 이하로 입력해주세요.' },
            })}
            errors={errors}
          />
          <InfoInput
            name="hobby"
            title="관심사"
            register={register('hobby', {
              required: '관심사를 입력해주세요.',
              maxLength: { value: 20, message: '20글자 이하로 입력해주세요.' },
            })}
            errors={errors}
          />
          <div>
            <DoneButton title="완료" />
          </div>
        </div>
      </div>
    </form>
  );
};

export default ModiUserAddInfo;
