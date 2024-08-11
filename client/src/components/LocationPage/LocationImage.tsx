interface LocationImageProps{
    top?:number;
    bottom?:number;
    left?:number;
    right?:number;
    profileImage?:string;

}

const LocationImage = ({top, bottom, left, right, profileImage}:LocationImageProps)=>{
    return <div className={`absolute ${top ? `top-${top}` : ''} ${bottom ? `bottom-${bottom}` : ''} ${left ? `left-${left}` : ''} ${right ? `right-${right}` : ''}`}>
        <img className="rounded-full h-16 w-16" src={profileImage} alt="" />
    </div>
}
export default LocationImage