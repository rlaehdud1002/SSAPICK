import Loading from 'components/common/Loading';
import ToPlusIcon from 'icons/ToPlusIcon';

interface FriendRecommendProps {
  cohort: number;
  classNum: number;
  name: string;
  profileImage: string;
  userId: number;
}

const FriendRecommendContent = ({ cohort, classNum, name, profileImage, userId }: FriendRecommendProps) => {
  return (
    <div className='flex'>
      <div className="flex overflow-x-scroll scrollbar-hide">
      <ToPlusIcon cohort={cohort} classNum={classNum} name={name} profileImage={profileImage} userId={userId} />
      </div>
    </div>
  );
};
export default FriendRecommendContent;
