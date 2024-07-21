interface CameraIconProps {
    width: number;
    height: number;
}

const CameraIcon = ({width,height}:CameraIconProps) => {
    return (
    <img 
    className="cursor-pointer"
      width={width}
      height={height}
      src="/icons/Camera.png"
      alt="camera"
    />
    )
}

export default CameraIcon;