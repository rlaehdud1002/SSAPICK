import HintModal from 'components/modals/HintModal';
import MessageModal from 'components/modals/MessageModal';
import UserMaskIcon from 'icons/UserMaskIcon';

import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from 'components/ui/accordion';

import { IPick } from 'atoms/Pick.type';

interface ResponseProps {
  picks: IPick[];
  isLoading: boolean;
}

const Response = ({ picks, isLoading }: ResponseProps) => {
  return (
    <div className="rounded-lg bg-white/50 p-4">
      {picks.map((pick, index) => (
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
                  <HintModal
                    title={
                      pick.openedHints.length === 0 ? '?' : pick.openedHints[0]
                    }
                  />
                </div>
                <div className="rounded-lg bg-white/50 p-3 mx-10 w-20 text-center">
                  <HintModal
                    title={
                      pick.openedHints.length === 1 ? '?' : pick.openedHints[1]
                    }
                  />
                </div>
              </div>
              {!pick.messageSend && (
                <div className="float-end">
                  <MessageModal
                    receiverId={pick.receiver.userId}
                    pickId={pick.id}
                  />
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
