import LocationModal from "components/modals/LocationModal";
import { profile } from "console";

interface LocationImageProps {
    top: number;
    left: number;
    profileImage: string;
    username: string;
}

const LocationImage = ({ top, left, profileImage, username }: LocationImageProps) => {
    return <div
        className="absolute"
        style={{
            top: top !== undefined ? `${top}px` : 'undefined',
            left: left !== undefined ? `${left}px` : 'undefined',
        }} >
        <div className="bg-blue-400 rounded-full w-14 h-14 flex items-center justify-center">
        <LocationModal  profileImage={profileImage} username={username} />
        </div>
    </div>
}
export default LocationImage
