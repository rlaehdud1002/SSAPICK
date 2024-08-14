import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { getFriendsList } from 'api/friendApi';
import { getPickInfo, postCreatePick } from 'api/pickApi';
import { getQuestion } from 'api/questionApi';
import { IFriend } from 'atoms/Friend.type';
import { pickFriendState } from 'atoms/FriendAtoms';
import { IPickCreate, IPickInfo, IQuestion } from 'atoms/Pick.type';
import { isQuestionUpdatedState, questionState } from 'atoms/PickAtoms';
import Choice from 'components/PickPage/ChoiceBox';
import NoFourFriends from 'components/PickPage/NoFourFriends';
import PickComplete from 'components/PickPage/PickComplete';
import Question from 'components/PickPage/QuestionBox';
import Loading from 'components/common/Loading';
import FriendRerollModal from 'components/modals/FriendRerollModal';
import CoolTime from 'pages/CoolTimePage';
import { useCallback, useEffect, useState, useTransition } from 'react';
import { useRecoilState } from 'recoil';

const Pick = () => {
  const [question, setQuestion] = useRecoilState<IQuestion[]>(questionState);
  const [finish, setFinish] = useState<boolean>(false);
  const [isTouchDisabled, setIsTouchDisabled] = useState<boolean>(false);

  const getNewQuestion = useMutation({
    mutationKey: ['question'],
    mutationFn: getQuestion,
    onSuccess: (data) => {
      setQuestion(data);
    },
  });

  const { data: friends = [], isLoading: LoadingFriendLists } = useQuery<
    IFriend[]
  >({
    queryKey: ['friends'],
    queryFn: getFriendsList,
  });

  const [pickFriends, setPickFriends] =
    useRecoilState<IFriend[]>(pickFriendState);

  const handleShuffle = useCallback(() => {
    if (friends.length >= 4) {
      const shuffledFriends = friends.sort(() => Math.random() - 0.5);
      setPickFriends(shuffledFriends.slice(0, 4));
    }
  }, [friends, setPickFriends]);

  useEffect(() => {
    if (friends.length >= 4 && pickFriends.length === 0) {
      handleShuffle();
    }
  }, [friends, handleShuffle, pickFriends]);

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

  const [isPending, startTransition] = useTransition();

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

  const handleUserPick = (data: IPickCreate) => {
    setIsTouchDisabled(true);
    startTransition(() => {
      mutation.mutate(data);
    });

    setTimeout(() => {
      setIsTouchDisabled(false);
    }, 150);
  };

  if (LoadingFriendLists || LoadingPickInfo || !isLoaded || !pickInfo) {
    return <Loading />;
  }

  if (finish) {
    return <PickComplete setQuestion={setQuestion} />;
  } else if (!LoadingFriendLists && friends.length < 4) {
    return <NoFourFriends />;
  }

  return (
    <div className="relative">
      {pickInfo.cooltime ? (
        <CoolTime endTime={pickInfo.endTime} />
      ) : (
        pickFriends &&
        question[pickInfo.index] && (
          <div
            className={`${isPending || isTouchDisabled ? 'pointer-events-none' : ''}`}
          >
            <Question
              question={question[pickInfo.index]}
              userPick={handleUserPick}
              pickInfo={pickInfo}
            />
            <div className="m-6">
              <div className="flex flex-row justify-center">
                <Choice
                  isTouchDisabled={isTouchDisabled}
                  friend={pickFriends[0]}
                  questionId={question[pickInfo.index].id}
                  userPick={handleUserPick}
                />
                <Choice
                  isTouchDisabled={isTouchDisabled}
                  friend={pickFriends[1]}
                  questionId={question[pickInfo.index].id}
                  userPick={handleUserPick}
                />
              </div>
              <div className="flex flex-row justify-center">
                <Choice
                  isTouchDisabled={isTouchDisabled}
                  friend={pickFriends[2]}
                  questionId={question[pickInfo.index].id}
                  userPick={handleUserPick}
                />
                <Choice
                  isTouchDisabled={isTouchDisabled}
                  friend={pickFriends[3]}
                  questionId={question[pickInfo.index].id}
                  userPick={handleUserPick}
                />
              </div>
            </div>
            <div className="flex justify-center space-x-2">
              <FriendRerollModal handleShuffle={handleShuffle} />
            </div>
          </div>
        )
      )}
    </div>
  );
};

export default Pick;
