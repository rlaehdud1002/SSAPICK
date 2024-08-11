import { useQuery } from "@tanstack/react-query";
import { getUserInfo } from "api/authApi";
import { IUserInfo } from "atoms/User.type";
import { profileImageState, sendUserInfoState } from "atoms/UserAtoms";
import DoneButton from "buttons/DoneButton";
import ProfileCameraIcon from "icons/ProfileCameraIcon";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { useRecoilValue, useSetRecoilState } from "recoil";
import InfoInput from "./InfoInput";
import InfoSelect from "./InfoSelect";
import { isValidateState, validState } from "atoms/ValidAtoms";
import { useNavigate } from "react-router-dom";
import Loading from "components/common/Loading";

interface UserForm {
  name: string;
  gender: string;
  th: number;
  campus: string;
  class: number;
}

interface UserInfoProps {
  next: () => void;
}

const UserInfo = ({ next }: UserInfoProps) => {
  const setSendUserInfo = useSetRecoilState(sendUserInfoState);
  const setProfileImage = useSetRecoilState<File | undefined>(profileImageState);

  const isValid = useRecoilValue(isValidateState);
  const setValidState = useSetRecoilState(validState);

  const navigate = useNavigate();
  useEffect(() => {
    if (isValid) {
      navigate("/home");
    }
  });

  const {
    register,
    reset,
    handleSubmit,
    setValue,
    formState: { errors },
  } = useForm<UserForm>({
    defaultValues: {
      name: "",
      campus: "",
      gender: "",
      class: 0,
    },
  });

  const [uploadImage, setUploadImage] = useState<File | undefined>(undefined);

  const { data: information, isLoading } = useQuery<IUserInfo>({
    queryKey: ["information"],
    queryFn: async () => await getUserInfo(),
  });

  const onSubmit = (data: UserForm) => {
    setSendUserInfo((prev) => {
      return {
        ...prev,
        name: data.name,
        gender: data.gender,
        campusSection: data.class,
        cohort: data.th,
        campusName: data.campus,
      };
    });
    if (uploadImage) {
      setProfileImage(uploadImage);
    }
    next();
  };

  const onInvalid = (errors: any) => {
    console.log("error", errors);
  };

  console.log(information);

  useEffect(() => {
    if (!isLoading && information) {
      // setProfileImage(information.profileImage);
      console.log(information);
      reset({
        name: information.name || "",
        gender: information.gender || "",
        class: information.section || 0,
        campus: information.campusName || "",
      });
    }
  }, [information, isLoading, reset]);

  if (isLoading) return <Loading />;

  return (
    <form onSubmit={handleSubmit(onSubmit, onInvalid)}>
      <div className="flex w-full flex-col justify-center items-center mt-10 space-y-2">
        <div className="mb-10">
          <ProfileCameraIcon
            defaultImage={information?.profileImage}
            setUploadImage={setUploadImage}
          />
        </div>
        <InfoInput
          name="name"
          title="이름"
          register={register("name", {
            required: "이름을 입력해주세요.",
            maxLength: { value: 10, message: "10글자이하로 입력해주세요." },
          })}
          value={information?.name}
          errors={errors}
        />

        <InfoSelect
          name="gender"
          title="성별"
          register={register("gender", {
            required: "성별을 선택해주세요.",
          })}
          setValue={(value: string) => setValue("gender", value)}
          defaultValue={information?.gender}
          errors={errors}
        />
        <InfoSelect
          name="class"
          title="반"
          register={register("class", {
            required: "반을 선택해주세요.",
          })}
          setValue={(value: number) => setValue("class", value)}
          defaultValue={information?.section}
          errors={errors}
        />

        <InfoSelect
          name="th"
          title="기수"
          register={register("th", {
            required: "기수를 선택해주세요.",
          })}
          setValue={(value: number) => setValue("th", value)}
          errors={errors}
        />

        <InfoSelect
          name="campus"
          title="캠퍼스"
          register={register("campus", {
            required: "캠퍼스를 선택해주세요.",
          })}
          setValue={(value: string) => setValue("campus", value)}
          defaultValue={information?.campusName}
          errors={errors}
        />
        <div>
          <DoneButton title="다음" />
        </div>
      </div>
    </form>
  );
};

export default UserInfo;
