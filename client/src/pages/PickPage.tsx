import Question from 'components/PickPage/QuestionBox';
import Choice from 'components/PickPage/ChoiceBox';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { IPickCreate, IPickInfo, IQuestion } from 'atoms/Pick.type';
import { getQuestion } from 'api/questionApi';
import { IFriend } from 'atoms/Friend.type';
import { getFriendsList } from 'api/friendApi';
import { useCallback, useState, useEffect } from 'react';
import { getPickInfo, postCreatePick } from 'api/pickApi';
import { useRecoilState } from 'recoil';
import { questionState } from 'atoms/PickAtoms';
import PickComplete from 'components/PickPage/PickComplete';
import { Navigate } from 'react-router-dom';
import FriendRerollModal from 'components/modals/FriendRerollModal';

const Pick = () => {
  // ========================================== 질문 조회 ==============================================================
  // recoil에 저장되어 있는 질문들
  const [question, setQuestion] = useRecoilState<IQuestion[]>(questionState);
  const [finish, setFinish] = useState<boolean>(false);

  // 새로운 질문 조회 mutation
  const getNewQuestion = useMutation({
    mutationKey: ['question'],
    mutationFn: getQuestion,

    onSuccess: (data) => {
      setQuestion(data);
    },
  });

  useEffect(() => {
    getNewQuestion.mutate();
  }, []);

  // ========================================== 친구 조회 ==============================================================
  // 전체 친구 목록 조회
  const { data: friends = [], isLoading: LoadingFriendLists } = useQuery<
    IFriend[]
  >({
    queryKey: ['friends'],
    queryFn: getFriendsList,
  });

  // 랜덤으로 친구 4명 조회
  const [pickFriends, setPickFriends] = useState<IFriend[]>([]);

  const handleShuffle = useCallback(() => {
    if (friends.length > 0) {
      const shuffledFriends = friends.sort(() => Math.random() - 0.5);
      setPickFriends(shuffledFriends.slice(0, 4));
    }
  }, [friends]);

  useEffect(() => {
    if (friends.length > 0) {
      handleShuffle();
    }
  }, [handleShuffle, friends]);

  // ============================================== 픽 생성 ========================================================='
  const [nowQuestion, setNowQuestion] = useState<IQuestion>();
  const [isLoaded, setIsLoaded] = useState<boolean>(false);

  // pick 상태 조회
  const { data: pickInfo, isLoading: LoadingPickInfo } = useQuery<IPickInfo>({
    queryKey: ['pickInfo'],
    queryFn: getPickInfo,
  });

  const queryClient = useQueryClient();

  useEffect(() => {
    if (pickInfo && question.length > 0) {
      setNowQuestion(question[pickInfo.index]);
      setIsLoaded(true); // 데이터가 로딩된 후 상태를 업데이트

      // index === 0 이면 질문 시작이므로 새로운 질문 조회
      // if (pickInfo.index === 0) {
      //   getNewQuestion.mutate();
      //   console.log('새로운 질문 조회');
      // }
    }
  }, [pickInfo, question]);

  // pick 생성
  const mutation = useMutation({
    mutationKey: ['pickInfo'],
    mutationFn: async (data: IPickCreate) => postCreatePick(data),
    onSuccess: (data: IPickInfo) => {
      queryClient.invalidateQueries({
        queryKey: ['pickInfo'],
      });
      setNowQuestion(question[data.index]);
      handleShuffle();

      // post의 결과가 null이면 PickComplete로 이동
      setFinish(data.index === null);
    },
  });

  if (finish) {
    return <PickComplete setQuestion={setQuestion} />;
  }

  if (LoadingFriendLists || LoadingPickInfo || !isLoaded || !pickInfo) {
    return <div>데이터 준비중입니다.</div>;
  }

  return (
    <div className="relative">
      {pickInfo?.cooltime ? (
        <Navigate to="/cooltime" />
      ) : (
        nowQuestion &&
        pickFriends.length > 0 && (
          <div>
            <Question
              question={nowQuestion}
              userPick={mutation.mutate}
              pickInfo={pickInfo}
            />
            <div className="m-7">
              <div className="flex flex-row justify-end">
                <FriendRerollModal handleShuffle={handleShuffle} />
              </div>
              <div className="flex flex-row justify-center">
                <Choice
                  friend={pickFriends[0]}
                  questionId={nowQuestion.id}
                  userPick={mutation.mutate}
                />
                <Choice
                  friend={pickFriends[1]}
                  questionId={nowQuestion.id}
                  userPick={mutation.mutate}
                />
              </div>
              <div className="flex flex-row justify-center">
                <Choice
                  friend={pickFriends[2]}
                  questionId={nowQuestion.id}
                  userPick={mutation.mutate}
                />
                <Choice
                  friend={pickFriends[3]}
                  questionId={nowQuestion.id}
                  userPick={mutation.mutate}
                />
              </div>
            </div>
          </div>
        )
      )}
    </div>
  );
};

export default Pick;
