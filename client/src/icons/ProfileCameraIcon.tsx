import React, { useEffect } from "react";

interface ProfileCameraIconProps {
  setUploadImage: any;
}

const ProfileCameraIcon = ({ setUploadImage }: ProfileCameraIconProps) => {
  const [imageSrc, setImageSrc] = React.useState<string>("/icons/Profile.png")
  function handleFileUpload(event: React.ChangeEvent<HTMLInputElement>) {
    const files = event.target.files;

    if (files === null || files.length === 0) {
      return;
    }

    const file = files[0];

    const reader = new FileReader();
    reader.onload = (e) => {
      setUploadImage(file)
      setImageSrc(e.target?.result as string)
    }
    reader.readAsDataURL(file);
  }

  useEffect(() => {
    console.log(imageSrc)
  }, [imageSrc])


  return (

    <div className="relative">
      <img width={180} height={180} src={imageSrc} alt="profile" />
      <div className="absolute w-10 bottom-1 right-2">
        <label htmlFor="profile-image">
          <img src="/icons/Camera.png" alt="camera" />
          <input accept="image/*" onChange={handleFileUpload} className="hidden" id="profile-image" type="file" />
        </label>

      </div>
    </div>

  )
}

export default ProfileCameraIcon;
