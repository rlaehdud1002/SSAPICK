import Question from 'components/PickPage/QuestionBox';
import Choice from 'components/PickPage/ChoiceBox';
import ShuffleIcon from 'icons/ShuffleIcon';
import { useQuery } from '@tanstack/react-query';
import { IQuestion } from 'atoms/Pick.type';
import { getQuestion } from 'api/questionApi';
import { IFriend } from 'atoms/Friend.type';
import { getFriendsList } from 'api/friendApi';
import { useCallback, useEffect, useState } from 'react';
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
  const { data: friends = [], isLoading: LoadingFriendLists } = useQuery<
    IFriend[]
  >({
    queryKey: ['friends'],
    queryFn: async () => await getFriendsList(),
  });

  // 랜덤으로 친구 4명 조회
  const [pickFriends, setPickFriends] = useState<IFriend[]>([]);

  const handleShuffle = useCallback(() => {
    const shuffledFriends = friends.sort(() => Math.random() - 0.5);
    setPickFriends(shuffledFriends.slice(0, 4));
    console.log('click', pickFriends);
  }, []);

  useEffect(() => {
    handleShuffle();
  }, [handleShuffle]);

  return (
    <div className="relative">
      {/* <PickComplete /> */}
      {/* <CoolTime /> */}
      <div>
        {questions &&
          questions.length > 0 &&
          questions.map((question, index) => {
            return <Question question={question} key={index} />;
          })}
        {pickFriends.length >= 4 && (
          <div className="m-7">
            <div className="flex flex-row justify-end" onClick={handleShuffle}>
              <ShuffleIcon className="cursor-pointer" />
            </div>
            <div className="flex flex-row justify-center">
              <Choice
                username={pickFriends[0].nickname}
                gen={pickFriends[0].gender}
              />
              <Choice
                username={pickFriends[1].nickname}
                gen={pickFriends[1].gender}
              />
            </div>
            <div className="flex flex-row justify-center">
              <Choice
                username={pickFriends[2].nickname}
                gen={pickFriends[2].gender}
              />
              <Choice
                username={pickFriends[3].nickname}
                gen={pickFriends[3].gender}
              />
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default Pick;
