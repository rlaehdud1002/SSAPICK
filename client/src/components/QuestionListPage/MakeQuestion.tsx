import { useQuery } from "@tanstack/react-query";
import MakeQuestionContent from "./MakeQuestionlContent";
import { getQuestionByUser } from "api/questionApi";
import { IQuestionNoCreatedAt } from "atoms/Pick.type";

const MakeQuestion = () => {
  const { data: questions, isLoading } = useQuery<IQuestionNoCreatedAt[]>({
    queryKey: ["question", "me"],
    queryFn: async () => await getQuestionByUser(),
  });

  if (isLoading) {
    return <div>로딩 중...</div>;
  }

  console.log("questions : ", questions);

  if (!questions || questions.length === 0) {
    return <div>질문이 없습니다.</div>;
  }

  return (
    <div className="mb-20">
      {questions.map((question, index) => (
        <MakeQuestionContent key={index} question={question.content} />
      ))}
    </div>
  );
};

export default MakeQuestion;
