import Question from 'components/PickPage/QuestionBox';
import Choice from 'components/PickPage/ChoiceBox';
import ShuffleIcon from 'icons/ShuffleIcon';
import { QueryClient, useMutation, useQuery } from '@tanstack/react-query';
import { IPickCreate, IPickInfo, IQuestion } from 'atoms/Pick.type';
import { getQuestion } from 'api/questionApi';
import { IFriend } from 'atoms/Friend.type';
import { getFriendsList } from 'api/friendApi';
import { useCallback, useState, useEffect } from 'react';
import { getPickInfo, postCreatePick } from 'api/pickApi';
import { useRecoilState } from 'recoil';
import { questionState } from 'atoms/PickAtoms';
import CoolTime from 'components/PickPage/CoolTime';
import PickComplete from 'components/PickPage/PickComplete';

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

  // 질문 리스트가 비어있을 때 질문 조회
  useEffect(() => {
    // if (question.length === 0) getNewQuestion.mutate();
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

  // ============================================== 픽 생성 ========================================================='
  const [nowQuestion, setNowQuestion] = useState<IQuestion>();
  const [isLoaded, setIsLoaded] = useState<boolean>(false);
  const queryClient = new QueryClient();

  // pick 상태 조회
  const { data: pickInfo, isLoading: LoadingPickInfo } = useQuery({
    queryKey: ['pickInfo'],
    queryFn: getPickInfo,
  });

  useEffect(() => {
    if (pickInfo && question.length > 0) {
      setNowQuestion(question[pickInfo.index]);
      setIsLoaded(true); // 데이터가 로딩된 후 상태를 업데이트
    }
  }, [pickInfo, question]);

  // pick 생성
  const mutation = useMutation({
    mutationKey: ['createPick'],
    mutationFn: async (data: IPickCreate) => postCreatePick(data),

    onSuccess: (data: IPickInfo) => {
      queryClient.invalidateQueries({
        queryKey: ['pickInfo'],
      });
      setNowQuestion(question[data.index]);
      handleShuffle();
      console.log('pickInfo', pickInfo);
    },
  });

  if (LoadingFriendLists || LoadingPickInfo || !isLoaded || !pickInfo) {
    return <div>데이터 준비중입니다.</div>;
  }

  return (
    <div className="relative">
      {/* <PickComplete setQuestion={setQuestion} /> */}
      {pickInfo?.cooltime ? (
        <CoolTime />
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
              <div
                className="flex flex-row justify-end"
                onClick={handleShuffle}
              >
                <ShuffleIcon className="cursor-pointer" />
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
