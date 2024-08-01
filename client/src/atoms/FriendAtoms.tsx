import { IFriend } from 'atoms/Friend.type';
import { atom } from 'recoil';


export const friendListState = atom<IFriend[]>({
  key: 'friendListState',
  default: [
    {
      userId: 1,
      nickname: '민준수',
      gender: "F",
      campusName: "광주",
      campusSection: 2,
      campusDescription: "자바 전공",
      profileImage: "./icons/ProfileIcon.png",
    },
    {
      userId: 2,
      nickname: '이호영',
      gender: "F",
      campusName: "광주",
      campusSection: 8,
      campusDescription: "자바 전공",
      profileImage: "./icons/ProfileIcon.png",
    },
  ]
});


// const nonFriendListState = atom<IFriend[]>({
//   key: 'nonFriendListState',
//   default: dummyFriends
// });