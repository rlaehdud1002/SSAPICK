import instance from "api/clientApi";
import { ICreateQuestion, IQuestion, IQuestionNoCreatedAt } from "atoms/Pick.type";
import { BaseResponse } from "atoms/User.type";

// 로그인 된 사용자에게 맞는 질문 조회
export const getQuestion = async (): Promise<IQuestion[]> => {
  const {
    data: { success, data },
  } = await instance.get<BaseResponse<IQuestion[]>>("/questions/pick");

  if (!success) {
    throw new Error("질문 조회 실패");
  }

  console.log("getQuestion");
  return data;
};

// 질문 생성
export const postCreateQuestion = async (questionData: ICreateQuestion): Promise<void> => {
  const {
    data: { success },
  } = await instance.post<BaseResponse<null>>("/questions", questionData);

  if (!success) {
    throw new Error("질문 생성 실패");
  }

  console.log("postCreateQuestion");
};

// 내가 받은 질문 TOP3 조회
export const getMyQuestionRank = async (): Promise<IQuestionNoCreatedAt[]> => {
  const {
    data: { success, data },
  } = await instance.get<BaseResponse<IQuestionNoCreatedAt[]>>("/questions/rank");

  if (!success) {
    throw new Error("받은 질문 조회 실패");
  }

  console.log("getReceiveQuestion", data);
  return data;
};

// 유저 아이디로 질문 조회
export const getQuestionByUser = async (): Promise<IQuestionNoCreatedAt[]> => {
  const {
    data: { success, data },
  } = await instance.get<BaseResponse<IQuestionNoCreatedAt[]>>(`/questions/me`);

  console.log("data", data);

  if (!success) {
    throw new Error("질문 조회 실패");
  }

  console.log("getQuestionById");
  return data;
};
