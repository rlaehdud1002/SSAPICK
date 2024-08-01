import { userState } from 'atoms/UserAtoms';
import DoneButton from 'buttons/DoneButton';
import InfoInput from 'components/LoginPage/InfoInput';
import InfoSelect from 'components/LoginPage/InfoSelect';
import ProfileCameraIcon from 'icons/ProfileCameraIcon';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import { useRecoilState } from 'recoil';

interface UserForm {
  name: string;
  gender: string;
  th: string;
  campus: string;
}

const ModiUserInfo = () => {
  const [UserInfo, setUserInfo] = useRecoilState(userState)
  const {
    register,
    handleSubmit,
    setValue,
    formState: { errors },
  } = useForm<UserForm>({
    defaultValues: {
      name: UserInfo.name,
      gender: UserInfo.gender,
      th: UserInfo.th,
      campus: UserInfo.campusName,
    }
  });


  const [setUploadImage] = useState<File | undefined>(undefined);

  const navigate = useNavigate();
  const navigateToAddInfo = () => {
    navigate('/profile/modiuseraddinfo');
  };

  const onSubmit = (data: UserForm) => {
    navigateToAddInfo();
    const form = new FormData();
    form.append('name', data.name);
    form.append('image', '');
    console.log(data);
    // recoil state 변경
    setUserInfo((prev) => ({
      ...prev,
      name: data.name,
      gender: data.gender,
      th: data.th,
      campusName: data.campus
    }))
  };

  const onInvalid = (errors: any) => {
    console.log('error', errors);
  };

  return (
    <form onSubmit={handleSubmit(onSubmit, onInvalid)}>
      <div className="ml-8 my-2">정보 수정</div>
      <div className="flex w-full flex-col justify-center items-center mt-10 space-y-2">
        <div className="mb-10">
          <ProfileCameraIcon setUploadImage={setUploadImage} />
        </div>

        {/* <div className="mb-20" style={{ color: "red", fontSize: 10 }}>모든 정보 입력이 필수입니다.</div> */}

        <InfoInput
          name="name"
          title="이름"
          register={register('name', {
            required: '이름을 입력해주세요.',
            maxLength: { value: 10, message: '10글자로 입력해주세요.' },
          })}
          errors={errors}
          value={UserInfo.name}
        />

        <InfoSelect
          name="gender"
          title="성별"
          register={register('gender', {
            required: '성별을 선택해주세요.',
          })}
          setValue={(value: string) => setValue('gender', value)}
          errors={errors}
          defaultValue={UserInfo.gender}
        />

        <InfoSelect
          name="th"
          title="기수"
          register={register('th', {
            required: '기수를 선택해주세요.',
          })}
          setValue={(value: string) => setValue('th', value)}
          errors={errors}
          defaultValue={UserInfo.th}
        />

        <InfoSelect
          name="campus"
          title="캠퍼스"
          register={register('campus', {
            required: '캠퍼스를 선택해주세요.',
          })}
          setValue={(value: string) => setValue('campus', value)}
          errors={errors}
          defaultValue={UserInfo.campusName}
        />
        <div className="flex">
          <div className="bg-white w-3 h-3 rounded-full my-2 mx-1"></div>
          <div className="bg-white w-3 h-3 rounded-full my-2 mx-1 opacity-50"></div>
        </div>
        <div>
          <DoneButton title="다음" />
        </div>
      </div>
    </form>
  );
};

export default ModiUserInfo;
