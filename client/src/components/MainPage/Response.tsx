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
import { useCallback, useEffect, useState } from 'react';

interface ResponseProps {
  picks: IPick[];
  isLoading: boolean;
}

const Response = ({ picks, isLoading }: ResponseProps) => {
  const [updatedPicks, setUpdatedPicks] = useState<IPick[]>([]);

  useEffect(() => {
    setUpdatedPicks(picks);
  }, [picks]);

  const handleAlarmUpdate = (pickId: number) => {
    setUpdatedPicks((prevPicks) => {
      const newPicks = prevPicks.map((pick) => {
        if (pick.id === pickId) {
          return { ...pick, alarm: !pick.alarm };
        } else if (pick.alarm) {
          return { ...pick, alarm: false };
        }
        return pick;
      });
      return newPicks;
    });
  };

  const handleAccordionClick = useCallback((e: React.MouseEvent) => {
    e.stopPropagation();
  }, []);

  const handleMaskClick = useCallback((e: React.MouseEvent) => {
    e.stopPropagation();
  }, []);

  return (
    <div>
      {updatedPicks.map((pick) => (
        <div key={pick.id} className="rounded-lg bg-white/50 p-4 mb-5">
          <Accordion type="single" collapsible>
            <AccordionItem value="item-1" className="border-none">
              <AccordionTrigger className="p-0" onClick={handleAccordionClick}>
                <div className="flex flex-col">
                  <div className="flex flex-row">
                    <div onClick={handleMaskClick}>
                      <UserMaskIcon
                        pickId={pick.id}
                        alarm={pick.alarm}
                        gen={pick.sender.gender}
                        onAlarmUpdate={handleAlarmUpdate}
                      />
                    </div>
                    <h3 className="mx-3 text-color-000855">
                      11기 {pick.sender.campusSection}반
                    </h3>
                  </div>
                </div>
              </AccordionTrigger>
              <p className="text-center my-4">{pick.question.content}</p>
              <AccordionContent>
                <div className="flex flex-row justify-center items-center">
                  <div className="rounded-lg bg-white/50 p-3 mx-10 `min`-w-20 max-w-40 text-center">
                    <HintModal
                      title={
                        pick.openedHints.length === 0
                          ? '?'
                          : pick.openedHints[0]
                      }
                      pickId={pick.id}
                    />
                  </div>
                  <div className="rounded-lg bg-white/50 p-3 mx-10 min-w-20 max-w-40 text-center">
                    <HintModal
                      title={
                        pick.openedHints.length <= 1 ? '?' : pick.openedHints[1]
                      }
                      pickId={pick.id}
                    />
                  </div>
                </div>
                {!pick.messageSend && (
                  <div className="float-end">
                    <MessageModal pick={pick} />
                  </div>
                )}
              </AccordionContent>
            </AccordionItem>
          </Accordion>
        </div>
      ))}
      <div className="h-24" />
    </div>
  );
};

export default Response;
