import UserIcon from '../icons/UserIcon';

import { Button } from 'components/ui/button';

import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from 'components/ui/accordion';
import HintModal from 'modals/HintModal';
import MessageFirstModal from 'modals/MessageFirstModal';
import MessageSecondModal from 'modals/MessageSecondModal';
import MessageThirdModal from 'modals/MessageThirdModal';

const Response = () => {
  return (
    <div className="rounded-md bg-white/50 p-4">
      <Accordion type="single" collapsible>
        <AccordionItem value="item-1" className="border-none">
          <AccordionTrigger className="p-0">
            <div className="flex flex-col">
              <div className="flex flex-row">
                <UserIcon />
                <h3 className="mx-3">12기 2반</h3>
              </div>
            </div>
          </AccordionTrigger>
          <p className="text-center mb-4">
            <Button />
            나랑 같이 프로젝트 하고 싶은 사람은?
          </p>
          <AccordionContent>
            <div className="flex flex-row justify-center">
              <div className="rounded-md bg-white/50 p-3 mx-10">
                <HintModal title="hint1" />
              </div>
              <div className="rounded-md bg-white/50 p-3 mx-10">
                <HintModal title="hint2" />
              </div>
            </div>
            <div className="float-end">
              <MessageFirstModal />
              <MessageSecondModal />
              <MessageThirdModal />
            </div>
          </AccordionContent>
        </AccordionItem>
      </Accordion>
    </div>
  );
};

export default Response;
