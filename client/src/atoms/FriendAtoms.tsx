import { dummyFriends } from 'dummy/friends';
import { atom } from 'recoil';
import { Friend, NonFriend } from './Friend.type';

export const friendListState = atom<Friend[]>({
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


const nonFriendListState = atom<NonFriend[]>({
  key: 'nonFriendListState',
  default: dummyFriends
});