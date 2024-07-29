import UserIcon from '../../icons/UserIcon';

import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from 'components/ui/accordion';

import HintModal from 'components/modals/HintModal';
import MessageModal from 'components/modals/MessageModal';

const Response = () => {
  return (
    <div className="rounded-lg bg-white/50 p-4">
      <Accordion type="single" collapsible>
        <AccordionItem value="item-1" className="border-none">
          <AccordionTrigger className="p-0">
            <div className="flex flex-col">
              <div className="flex flex-row">
                <UserIcon gen="female" />
                <h3 className="mx-3 text-color-000855">12기 2반</h3>
              </div>
            </div>
          </AccordionTrigger>
          <p className="text-center my-4">
            나랑 같이 프로젝트 하고 싶은 사람은?
          </p>
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
