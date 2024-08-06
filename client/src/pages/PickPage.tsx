import Question from "components/PickPage/QuestionBox";
import Choice from "components/PickPage/ChoiceBox";
import ShuffleIcon from "icons/ShuffleIcon";
import { useMutation, useQuery } from "@tanstack/react-query";
import { IPickCreate, IQuestion } from "atoms/Pick.type";
import { getQuestion } from "api/questionApi";
import { IFriend } from "atoms/Friend.type";
import { getFriendsList } from "api/friendApi";
import { useCallback, useState, useEffect } from "react";
import { postCreatePick } from "api/pickApi";
import { useRecoilState } from "recoil";
import { pickCountState } from "atoms/PickAtoms";
// import CoolTime from 'components/PickPage/CoolTime';
// import PickComplete from "components/PickPage/PickComplete";

const Pick = () => {
  // 인덱스 저장
  const [pickCount, setPickCount] = useRecoilState<number>(pickCountState); // post 요청 보낼 때 index로 보내기

  // 전체 질문 조회
  const { data: questions = [], isLoading: LoadingQuestions } = useQuery<IQuestion[]>({
    queryKey: ["questions"],
    queryFn: getQuestion,
  });

  // 전체 친구 목록 조회
  const { data: friends = [], isLoading: LoadingFriendLists } = useQuery<IFriend[]>({
    queryKey: ["friends"],
    queryFn: async () => await getFriendsList(),
  });

  // 랜덤으로 친구 4명 조회
  const [pickFriends, setPickFriends] = useState<IFriend[]>([]);

  const handleShuffle = useCallback(() => {
    const shuffledFriends = friends.sort(() => Math.random() - 0.5);
    setPickFriends(shuffledFriends.slice(0, 4));
    console.log("click", pickFriends);
  }, []);

  useEffect(() => {
    handleShuffle();
  }, [handleShuffle]);

  // pick 생성
  const mutation = useMutation({
    mutationKey: ["createPick"],
    mutationFn: postCreatePick,

    onSuccess: () => {
      console.log("pick 생성 성공");
      // 카운트 올리기
      setPickCount((prevCount) => (prevCount + 1) % 15);
    },
  });

  console.log("questionList", questions);

  return (
    <div className="relative">
      {/* <PickComplete /> */}
      {/* <CoolTime /> */}
      <div>
        {questions &&
          questions.length > 0 &&
          questions.map((question, index) => {
            return (
              <div key={index}>
                <Question question={question} userPick={mutation.mutate} />
                {pickFriends.length >= 4 && (
                  <div className="m-7">
                    <div className="flex flex-row justify-end" onClick={handleShuffle}>
                      <ShuffleIcon className="cursor-pointer" />
                    </div>
                    <div className="flex flex-row justify-center">
                      <Choice
                        friend={pickFriends[0]}
                        questionId={question.id}
                        userPick={mutation.mutate}
                      />
                      <Choice
                        friend={pickFriends[1]}
                        questionId={question.id}
                        userPick={mutation.mutate}
                      />
                    </div>
                    <div className="flex flex-row justify-center">
                      <Choice
                        friend={pickFriends[2]}
                        questionId={question.id}
                        userPick={mutation.mutate}
                      />
                      <Choice
                        friend={pickFriends[3]}
                        questionId={question.id}
                        userPick={mutation.mutate}
                      />
                    </div>
                  </div>
                )}
              </div>
            );
          })}
      </div>
    </div>
  );
};

export default Pick;
