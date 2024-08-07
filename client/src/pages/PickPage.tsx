import Question from 'components/PickPage/QuestionBox';
import Choice from 'components/PickPage/ChoiceBox';
import ShuffleIcon from 'icons/ShuffleIcon';
import { useMutation, useQuery } from '@tanstack/react-query';
import { IPickCreate, IQuestion } from 'atoms/Pick.type';
import { getQuestion } from 'api/questionApi';
import { IFriend } from 'atoms/Friend.type';
import { getFriendsList } from 'api/friendApi';
import { useCallback, useState, useEffect } from 'react';
import { postCreatePick } from 'api/pickApi';
import { useRecoilState } from 'recoil';
import { questionState } from 'atoms/PickAtoms';
// import CoolTime from 'components/PickPage/CoolTime';
// import PickComplete from "components/PickPage/PickComplete";

const Pick = () => {
  // ========================================== 질문 조회 ==============================================================
  // recoil에 저장되어 있는 질문들
  const [question, setQuestion] = useRecoilState<IQuestion[]>(questionState);

  // 새로운 질문 조회 mutation
  const getNewQuestion = useMutation({
    mutationKey: ['question'],
    mutationFn: getQuestion,

    onSuccess: (data) => {
      console.log('새로운 질문 조회 성공');
      setQuestion(data);
    },
  });

  useEffect(() => {
    getNewQuestion.mutate();
  }, []);

  // pick 생성
  const mutation = useMutation({
    mutationKey: ['createPick'],
    mutationFn: (data: IPickCreate) => postCreatePick(data),

    onSuccess: () => {
      console.log('pick 생성 성공');
    },
  });

  // ========================================== 친구 조회 ==============================================================
  // 전체 친구 목록 조회
  const { data: friends = [], isLoading: LoadingFriendLists } = useQuery<
    IFriend[]
  >({
    queryKey: ['friends'],
    queryFn: async () => await getFriendsList(),
  });

  // 랜덤으로 친구 4명 조회
  const [pickFriends, setPickFriends] = useState<IFriend[]>(
    friends.slice(0, 4),
  );

  const handleShuffle = useCallback(() => {
    if (friends.length > 0) {
      console.log('shuffleFriends');
      const shuffledFriends = friends.sort(() => Math.random() - 0.5);
      setPickFriends(shuffledFriends.slice(0, 4));
    }
  }, [friends]);

  useEffect(() => {
    if (friends.length > 0) {
      handleShuffle();
    }
  }, [handleShuffle, friends]);
  // =================================================================================================================

  return (
    <div className="relative">
      {/* <PickComplete /> */}
      {/* <CoolTime /> */}
      {question.length > 0 && pickFriends.length > 0 && (
        <div>
          <Question question={question[0]} userPick={mutation.mutate} />
          <div className="m-7">
            <div className="flex flex-row justify-end" onClick={handleShuffle}>
              <ShuffleIcon className="cursor-pointer" />
            </div>
            <div className="flex flex-row justify-center">
              <Choice
                friend={pickFriends[0]}
                questionId={question[0].id}
                userPick={mutation.mutate}
              />
              <Choice
                friend={pickFriends[1]}
                questionId={question[0].id}
                userPick={mutation.mutate}
              />
            </div>
            <div className="flex flex-row justify-center">
              <Choice
                friend={pickFriends[2]}
                questionId={question[0].id}
                userPick={mutation.mutate}
              />
              <Choice
                friend={pickFriends[3]}
                questionId={question[0].id}
                userPick={mutation.mutate}
              />
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Pick;
