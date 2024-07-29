import BlockFriendContent from "./BlockFriendContent"

const BlockFriend = () => {
    return (
        <div className="mb-20">
        {[0, 1, 2, 3, 4, 5, 6, 7, 8, 9].map((index) => (
            <BlockFriendContent key={index} th={11} classNum={2} name="민준수"/>
          ))}
        </div>
    )
}

export default BlockFriend