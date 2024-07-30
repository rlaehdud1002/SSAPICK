import UserMaskIcon from 'icons/UserMaskIcon';
import HintModal from 'components/modals/HintModal';
import MessageModal from 'components/modals/MessageModal';

import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from 'components/ui/accordion';

import { pickState } from 'atoms/PickAtoms';
import { useRecoilValue } from 'recoil';

const Response = () => {
  const pick = useRecoilValue(pickState);

  return (
    <div className="rounded-lg bg-white/50 p-4">
      <Accordion type="single" collapsible>
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
            <div className="float-end">
              <MessageModal />
            </div>
          </AccordionContent>
        </AccordionItem>
      </Accordion>
    </div>
  );
};

export default Response;
