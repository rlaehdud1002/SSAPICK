
import ToPlusIcon from "icons/ToPlusIcon";

const FriendSearchContent = () => {
  return (
    <div className="w-full">
      <div>
        <div className="flex overflow-x-scroll scrollbar-hide">
          {
            [0, 1, 2, 3, 4, 5, 6, 7, 8].map((item, index) => {
              return <ToPlusIcon campus="광주" th={11} classNum={2} name="민준수" isPlus={item % 2 === 0} />;
            })
          }
          {/* <ToPlusIcon campus="광주" th={11} classNum={2} name="민준수" isPlus={true} /> */}
        </div>
      </div>

    </div>
  )
}
export default FriendSearchContent;



