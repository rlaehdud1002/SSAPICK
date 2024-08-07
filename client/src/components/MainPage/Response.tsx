import HintModal from "components/modals/HintModal";
import MessageModal from "components/modals/MessageModal";
import UserMaskIcon from "icons/UserMaskIcon";

import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "components/ui/accordion";

import { IPick } from "atoms/Pick.type";
import { useCallback, useEffect, useState } from "react";

interface ResponseProps {
  picks: IPick[];
  isLoading: boolean;
}

const Response = ({ picks, isLoading }: ResponseProps) => {
  const [updatedPicks, setUpdatedPicks] = useState<IPick[]>(picks);

  useEffect(() => {
    console.log("updatedPicks", updatedPicks);
    setUpdatedPicks(updatedPicks);
  }, [updatedPicks]);

  const handleAlarmUpdate = (newPicks: IPick[]) => {
    setUpdatedPicks(newPicks);
  };

  const handleAccordionClick = useCallback((e: React.MouseEvent) => {
    e.preventDefault();
  }, []);

  if (isLoading) {
    return <div>로딩중...</div>;
  }

  return (
    <div>
      {updatedPicks.map((pick, index) => (
        <div className="rounded-lg bg-white/50 p-4 mb-5">
          <Accordion key={index} type="single" collapsible>
            <AccordionItem value="item-1" className="border-none">
              <AccordionTrigger className="p-0" onClick={handleAccordionClick}>
                <div className="flex flex-col">
                  <div className="flex flex-row">
                    <UserMaskIcon
                      pickId={pick.id}
                      alarm={pick.alarm}
                      gen={pick.sender.gender}
                      onAlarmUpdate={handleAlarmUpdate}
                    />
                    <h3 className="mx-3 text-color-000855">11기 {pick.sender.campusSection}반</h3>
                  </div>
                </div>
              </AccordionTrigger>
              <p className="text-center my-4">{pick.question.content}</p>
              <AccordionContent>
                <div className="flex flex-row justify-center">
                  <div className="rounded-lg bg-white/50 p-3 mx-10 w-20 text-center">
                    <HintModal
                      title={pick.openedHints.length === 0 ? "?" : pick.openedHints[0]}
                      pickId={pick.id}
                    />
                  </div>
                  <div className="rounded-lg bg-white/50 p-3 mx-10 w-20 text-center">
                    <HintModal
                      title={pick.openedHints.length <= 1 ? "?" : pick.openedHints[1]}
                      pickId={pick.id}
                    />
                  </div>
                </div>
                {!pick.messageSend && (
                  <div className="float-end">
                    <MessageModal receiverId={pick.receiver.userId} pick={pick} />
                  </div>
                )}
              </AccordionContent>
            </AccordionItem>
          </Accordion>
        </div>
      ))}
    </div>
  );
};

export default Response;
