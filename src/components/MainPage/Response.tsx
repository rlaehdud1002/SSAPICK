import UserIcon from '../../icons/UserIcon';

import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from 'components/ui/accordion';

import { useState } from 'react';

import HintModal from 'components/modals/HintModal';
import MessageFirstModal from 'components/modals/MessageFirstModal';
import MessageSecondModal from 'components/modals/MessageSecondModal';
import CheckModal from 'components/modals/CheckModal';

const Response = () => {
  const [showFirstModal, setShowFirstModal] = useState(false);
  const [showSecondModal, setShowSecondModal] = useState(false);
  const [showCheckModal, setShowCheckModal] = useState(false);

  const openFirstModal = () => setShowFirstModal(true);
  const closeFirstModal = () => setShowFirstModal(false);
  const openSecondModal = () => setShowSecondModal(true);
  const closeSecondModal = () => setShowSecondModal(false);
  const openCheckModal = () => setShowCheckModal(true);
  const closeCheckModal = () => setShowCheckModal(false);

  return (
    <div className="rounded-md bg-white/50 p-4">
      <Accordion type="single" collapsible>
        <AccordionItem value="item-1" className="border-none">
          <AccordionTrigger className="p-0">
            <div className="flex flex-col">
              <div className="flex flex-row">
                <UserIcon />
                <h3 className="mx-3 text-color-000855">12기 2반</h3>
              </div>
            </div>
          </AccordionTrigger>
          <p className="text-center mb-4">
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
              <MessageFirstModal
                show={showFirstModal}
                onOpen={openFirstModal}
                onClose={closeFirstModal}
                onCreate={openSecondModal}
              />
              <MessageSecondModal
                show={showSecondModal}
                onClose={closeSecondModal}
                onCreate={openCheckModal}
              />
              <CheckModal
                title="쪽지 보내기"
                innerText="전송이 완료되었습니다."
                show={showCheckModal}
                onClose={closeCheckModal}
              />
            </div>
          </AccordionContent>
        </AccordionItem>
      </Accordion>
    </div>
  );
};

export default Response;
