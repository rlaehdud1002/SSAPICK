import instance from 'api/clientApi';
import { ICreateQuestion, IQuestion } from 'atoms/Pick.type';

// 로그인 된 사용자에게 맞는 질문 조회
export const getQuestion = async (): Promise<IQuestion[]> => {
  const {
    data: { success, data },
  } = await instance.get('/questions/pick');

  if (!success) {
    throw new Error('질문 조회 실패');
  }

  return data;
};

// 질문 생성
export const postCreateQuestion = async (
  questionData: ICreateQuestion,
): Promise<void> => {
  const {
    data: { success },
  } = await instance.post('/questions', questionData);

  if (!success) {
    throw new Error('질문 생성 실패');
  }
};
