import { IPickcolog } from 'atoms/Pickcolog.type';
import CoinIcon from 'icons/CoinIcon';

interface IPickcologContent {
  pickcolog: IPickcolog;
}

const formatDate = (dateString: string): string => {
  const date = new Date(dateString);
  const ampm = date.getHours() >= 12 ? '오후' : '오전';
  const hours = date.getHours() % 12 || 12;
  const minutes = date.getMinutes().toString().padStart(2, '0');

  return `${date.getMonth() + 1}월 ${date.getDate()}일 ${ampm} ${hours}시 ${minutes}분`;
};

const formatChange = (change: number): string => {
  return change > 0 ? `+${change}` : `${change}`;
};

const formatRemain = (remain: number): string => {
  return remain.toLocaleString();
};

const getTypeDetails = (type: string) => {
  switch (type) {
    case 'SIGN_UP':
      return {
        imageUrl:
          'https://d2yu3js5fxqm1g.cloudfront.net/category/%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85.png',
        title: '회원가입 보상',
      };
    case 'ATTENDANCE':
      return {
        imageUrl:
          'https://d2yu3js5fxqm1g.cloudfront.net/category/%EC%B6%9C%EC%84%9D.png',
        title: '출석 보상',
      };
    case 'HINT_OPEN':
      return {
        imageUrl:
          'https://d2yu3js5fxqm1g.cloudfront.net/category/%ED%9E%8C%ED%8A%B8.png',
        title: '힌트 열기',
      };
    case 'PICK':
      return {
        imageUrl: 'https://d2yu3js5fxqm1g.cloudfront.net/category/pick.png',
        title: '픽 보상',
      };
    case 'MESSAGE':
      return {
        imageUrl:
          'https://d2yu3js5fxqm1g.cloudfront.net/category/%EC%AA%BD%EC%A7%80.png',
        title: '메시지',
      };
    case 'QUESTION_CREATE':
      return {
        imageUrl:
          'https://d2yu3js5fxqm1g.cloudfront.net/category/%EC%A7%88%EB%AC%B8+%EC%83%9D%EC%84%B1.png',
        title: '질문 생성 보상',
      };
    case 'RE_ROLL':
      return {
        imageUrl:
          'https://d2yu3js5fxqm1g.cloudfront.net/category/%EC%B9%9C%EA%B5%AC2.png',
        title: '사용자 리롤',
      };
    default:
      return {
        imageUrl: '',
        title: '',
      };
  }
};

const PickcoLogList = ({ pickcolog }: IPickcologContent) => {
  const { change, pickcoLogType, remain, createdAt } = pickcolog;
  const changeTextColor = change > 0 ? 'text-[#5F86E9]' : 'text-[#E95F5F]';
  const { imageUrl, title } = getTypeDetails(pickcoLogType);

  const createTime = new Date(createdAt).getTime();
  const currentTime = new Date().getTime();
  const diffTime = currentTime - createTime;

  const months = String(
    Math.floor((diffTime / (1000 * 60 * 60 * 24 * 30)) % 12),
  );
  const days = String(Math.floor((diffTime / (1000 * 60 * 60 * 24)) % 30));
  const hours = String(Math.floor((diffTime / (1000 * 60 * 60)) % 24));
  const minutes = String(Math.floor((diffTime / (1000 * 60)) % 60));

  let receivedTime;

  if (months !== '0') {
    receivedTime = `${months}개월 전`;
  } else if (days !== '0') {
    receivedTime = `${days}일 전`;
  } else if (hours !== '0') {
    receivedTime = `${hours}시간 전`;
  } else if (minutes !== '0') {
    receivedTime = `${minutes}분 전`;
  } else {
    receivedTime = '방금';
  }

  return (
    <div className="bg-white/50 my-2 p-4 rounded-lg flex items-start space-x-4">
      <img
        src={imageUrl}
        alt={title}
        className="w-12 h-12 object-cover bg-white/50 rounded-full p-1"
      />
      <div className="flex flex-col justify-center flex-1">
        <span className="text-sapick font-semibold">{title}</span>
        <span className="text-gray-500 text-xs pt-2">{receivedTime}</span>
      </div>
      <div className="flex flex-col space-y-1">
        <span className={`font-semibold text-right ${changeTextColor}`}>
          {formatChange(change)}
        </span>
        <div className="flex flex-row items-center">
          <CoinIcon width={20} height={20} className="mr-1" />
          <span className="text-gray-600">{formatRemain(remain)}</span>
        </div>
      </div>
    </div>
  );
};

export default PickcoLogList;
