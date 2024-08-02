import Question from 'components/PickPage/QuestionBox';
import Choice from 'components/PickPage/ChoiceBox';
import ShuffleIcon from 'icons/ShuffleIcon';
import { useQuery } from '@tanstack/react-query';
import { IQuestion } from 'atoms/Pick.type';
import { getQuestion } from 'api/questionApi';
import { IFriend } from 'atoms/Friend.type';
import { getFriendsList } from 'api/friendApi';
import { useEffect, useState } from 'react';
// import CoolTime from 'components/PickPage/CoolTime';
// import PickComplete from "components/PickPage/PickComplete";

const Pick = () => {
  // 전체 질문 조회
  const { data: questions = [], isLoading: LoadingQuestions } = useQuery<
    IQuestion[]
  >({
    queryKey: ['questions'],
    queryFn: getQuestion,
  });

  // 전체 친구 목록 조회
  const { data: friends = [], isLoading: LoadingFriendLists } = useQuery<IFriend[]>({
    queryKey: ['friends'],
    queryFn: getFriendsList,
  });

  // ! FIXED : questions가 빈 배열은 ok인데 왜 undefined일까?
  console.log('questions', questions, LoadingQuestions);
  console.log('friends', friends, LoadingFriendLists);

  // 랜덤으로 친구 4명 조회
  const [pickFriends, setPickFriends] = useState<IFriend[]>([]);

  const handleShuffle = (friends: IFriend[]) => {
    // 랜덤으로 친구 4명 조회
    const randomFriends = friends.sort(() => Math.random() - 0.5).slice(0, 4);
    setPickFriends(randomFriends);
  };

  // useEffect(() => {
  //   handleShuffle();
  // }, []);

  return (
    <div className="relative">
      {/* <PickComplete /> */}
      {/* <CoolTime /> */}
      {questions &&
        questions.map((question, index) => {
          return (
            <div>
              <Question question={question} />
              <div className="m-7">
                <div className="flex flex-row justify-end">
                  <ShuffleIcon className="cursor-pointer" />
                </div>
                <div className="flex flex-row justify-center">
                  <Choice username="민준수" gen="M" />
                  <Choice username="이호영" gen="F" />
                </div>
                <div className="flex flex-row justify-center">
                  <Choice username="이인준" gen="M" />
                  <Choice username="황성민" gen="M" />
                </div>
              </div>
            </div>
          );
        })}
    </div>
  );
};

export default Pick;
