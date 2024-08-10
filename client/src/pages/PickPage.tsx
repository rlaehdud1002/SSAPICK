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
import { isQuestionUpdatedState, questionState } from 'atoms/PickAtoms';
import PickComplete from 'components/PickPage/PickComplete';
import { Navigate } from 'react-router-dom';
import FriendRerollModal from 'components/modals/FriendRerollModal';
import { pickFriendState } from 'atoms/FriendAtoms';

const Pick = () => {
  // ========================================== 질문 조회 ==============================================================
  const [question, setQuestion] = useRecoilState<IQuestion[]>(questionState);
  const [finish, setFinish] = useState<boolean>(false);

  const getNewQuestion = useMutation({
    mutationKey: ['question'],
    mutationFn: getQuestion,
    onSuccess: (data) => {
      setQuestion(data);
    },
  });

  // ========================================== 친구 조회 ==============================================================
  const { data: friends = [], isLoading: LoadingFriendLists } = useQuery<
    IFriend[]
  >({
    queryKey: ['friends'],
    queryFn: getFriendsList,
  });

  const [pickFriends, setPickFriends] =
    useRecoilState<IFriend[]>(pickFriendState);

  const handleShuffle = useCallback(() => {
    if (friends.length > 0) {
      const shuffledFriends = friends.sort(() => Math.random() - 0.5);
      setPickFriends(shuffledFriends.slice(0, 4));
    }
  }, [friends]);

  useEffect(() => {
    if (pickFriends.length === 0) {
      console.log('친구 셔플');
      handleShuffle();
    }
  }, [handleShuffle, friends]);

  // ============================================== 픽 생성 =========================================================
  const [isLoaded, setIsLoaded] = useState<boolean>(false);
  const [isUpdated, setIsUpdated] = useRecoilState<boolean>(
    isQuestionUpdatedState,
  );

  const { data: pickInfo, isLoading: LoadingPickInfo } = useQuery<IPickInfo>({
    queryKey: ['pickInfo'],
    queryFn: getPickInfo,
    refetchOnMount: false,
    staleTime: Infinity,
  });

  const queryClient = useQueryClient();

  useEffect(() => {
    if (pickInfo) {
      if (!isUpdated) {
        getNewQuestion.mutate();
        setIsUpdated(true);
      }
      setIsLoaded(true);
    }
  }, [pickInfo, question, isUpdated, getNewQuestion, setIsUpdated]);

  const mutation = useMutation({
    mutationKey: ['pickInfo'],
    mutationFn: async (data: IPickCreate) => postCreatePick(data),
    onSuccess: (data: IPickInfo) => {
      queryClient.invalidateQueries({
        queryKey: ['pickInfo'],
      });
      handleShuffle();
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
      {pickInfo.cooltime ? (
        <Navigate to="/cooltime" />
      ) : (
        question[pickInfo.index] && (
          <div>
            <Question
              question={question[pickInfo.index]}
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
                  questionId={question[pickInfo.index].id}
                  userPick={mutation.mutate}
                />
                <Choice
                  friend={pickFriends[1]}
                  questionId={question[pickInfo.index].id}
                  userPick={mutation.mutate}
                />
              </div>
              <div className="flex flex-row justify-center">
                <Choice
                  friend={pickFriends[2]}
                  questionId={question[pickInfo.index].id}
                  userPick={mutation.mutate}
                />
                <Choice
                  friend={pickFriends[3]}
                  questionId={question[pickInfo.index].id}
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
