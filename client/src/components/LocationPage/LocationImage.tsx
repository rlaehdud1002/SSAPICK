interface LocationImageProps {
    top?: number;
    bottom?: number;
    left?: number;
    right?: number;
    profileImage?: string;

}

const LocationImage = ({ top, bottom, left, right, profileImage }: LocationImageProps) => {
    return <div
        className="absolute"
        style={{
            top: top !== undefined ? `${top}px` : 'undefined',
            left: left !== undefined ? `${left}px` : 'undefined',
        }} >
        <div className="bg-blue-400 rounded-full w-14 h-14 flex items-center justify-center">
        <img className="rounded-full h-12 w-12" src={profileImage} alt="" />
        </div>
    </div>
}
export default LocationImage
