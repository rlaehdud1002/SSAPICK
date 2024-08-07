import { Popover, PopoverContent, PopoverTrigger } from 'components/ui/popover';
import PointIcon from 'icons/PointIcon';
import { useLocation } from 'react-router-dom';
import WarningDeleteModal from 'components/modals/WarningDeleteModal';
import { useMutation } from '@tanstack/react-query';
import { blockUser } from 'api/blockApi';

const WarningDelete = () => {
  // 유저 차단
  const mutatiion = useMutation({
    mutationKey: ['delete',],
    mutationFn: blockUser,
    onSuccess: () => {
      console.log('유저 차단 성공');
    }
  });
  const location = useLocation().pathname.split('/')[2];
  return (
    <Popover>
      <PopoverTrigger>
        <PointIcon />
      </PopoverTrigger>
      <PopoverContent className="mr-4 w-[102px] rounded-lg bg-[#E9F2FD] flex flex-col justify-center">
        {!(location === 'send') && (
          <div onClick={()=>{
            // messageId에서 userId로 입력
            mutatiion.mutate(1);
          }}>
          <WarningDeleteModal
            messageId={1}
            title="신고"
            message="쪽지 내용"
            location={location}
          />
          </div>
        )}
        <WarningDeleteModal messageId={1} title="삭제" location={location} />
      </PopoverContent>
    </Popover>
  );
};

export default WarningDelete;
