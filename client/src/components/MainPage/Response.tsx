import HintModal from 'components/modals/HintModal';
import MessageModal from 'components/modals/MessageModal';
import UserMaskIcon from 'icons/UserMaskIcon';

import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from 'components/ui/accordion';

import { QueryClient, useMutation, useQuery } from '@tanstack/react-query';
import { getReceivePick } from 'api/pickApi';
import { IPick } from 'atoms/Pick.type';

const Response = () => {
  const queryClient = new QueryClient();

  const { data: picks, isLoading } = useQuery<IPick[]>({
    queryKey: ['pick', 'receive'],
    queryFn: getReceivePick,
  });

  const mutation = useMutation({
    mutationKey: ['pick', 'send'],
    mutationFn: getReceivePick,
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ['pick', 'receive'],
      });
    },
  });

  // 쪽지 보내면 messaeSend를 true로 바꿈
  const sendMessage = () => {
    mutation.mutate();
  };

  return (
    <div className="rounded-lg bg-white/50 p-4">
      <HintModal title="?" />
      {picks &&
        picks.map((pick, index) => (
          <Accordion key={index} type="single" collapsible>
            <AccordionItem value="item-1" className="border-none">
              <AccordionTrigger className="p-0">
                <div className="flex flex-col">
                  <div className="flex flex-row">
                    <UserMaskIcon gen={pick.sender.gender} />
                    <h3 className="mx-3 text-color-000855">
                      11기 {pick.sender.campusSection}반
                    </h3>
                  </div>
                </div>
              </AccordionTrigger>
              <p className="text-center my-4">{pick.question.content}</p>
              <AccordionContent>
                <div className="flex flex-row justify-center">
                  <div className="rounded-lg bg-white/50 p-3 mx-10 w-20 text-center">
                    <HintModal title="?" />
                  </div>
                  <div className="rounded-lg bg-white/50 p-3 mx-10 w-20 text-center">
                    <HintModal title="?" />
                  </div>
                </div>
                {!pick.messageSend && (
                  <div className="float-end">
                    <MessageModal sendMessage={sendMessage} />
                  </div>
                )}
              </AccordionContent>
            </AccordionItem>
          </Accordion>
        ))}
    </div>
  );
};

export default Response;
