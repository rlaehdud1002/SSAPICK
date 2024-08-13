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
import { getPickco } from 'api/authApi';
import { useQuery } from '@tanstack/react-query';
import { IPickco } from 'atoms/User.type';
import Loading from 'components/common/Loading';
import AlarmCheckModal from 'components/modals/AlarmCheckModal';

interface ResponseProps {
  picks: IPick[];
}

const Response = ({ picks }: ResponseProps) => {
  const [updatedPicks, setUpdatedPicks] = useState<IPick[]>([]);
  const [show, setShow] = useState<boolean>(false);
  const [selectedQuestion, setSelectedQuestion] = useState<string | null>(null);
  const [selectedAlarm, setSelectedAlarm] = useState<boolean | null>(null);

  const { data: pickco, isLoading: isLoadingPickco } = useQuery<IPickco>({
    queryKey: ['pickco'],
    queryFn: getPickco,
  });

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

    const selectedPick = updatedPicks.find((pick) => pick.id === pickId);
    if (selectedPick) {
      setSelectedQuestion(selectedPick.question.content);
      setSelectedAlarm(selectedPick.alarm);
    }

    setShow(true);
  };

  const handleAccordionClick = useCallback((e: React.MouseEvent) => {
    e.stopPropagation();
  }, []);

  const handleMaskClick = useCallback((e: React.MouseEvent) => {
    e.stopPropagation();
  }, []);

  const handleMessageSent = useCallback((pickId: number) => {
    setUpdatedPicks((prevPicks) =>
      prevPicks.map((pick) =>
        pick.id === pickId ? { ...pick, messageSend: true } : pick,
      ),
    );
  }, []);

  if (isLoadingPickco || !pickco) {
    return <Loading />;
  }

  return (
    <div>
      {updatedPicks.map((pick, index) => (
        <div key={pick.id} className="rounded-lg bg-white/50 p-4 mb-5">
          <Accordion
            type="single"
            collapsible
            defaultValue={
              pick.openedHints.length !== 0 ? String(index) : undefined
            }
          >
            <AccordionItem value={String(index)} className="border-none">
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
                  <HintModal
                    title={
                      pick.openedHints.length === 0 ? '?' : pick.openedHints[0]
                    }
                    pickId={pick.id}
                    pickco={pickco.pickco}
                  />
                  <HintModal
                    title={
                      pick.openedHints.length <= 1 ? '?' : pick.openedHints[1]
                    }
                    pickId={pick.id}
                    pickco={pickco.pickco}
                  />
                </div>
                {!pick.messageSend && (
                  <div className="float-end">
                    <MessageModal
                      pick={pick}
                      pickco={pickco.pickco}
                      onMessageSent={() => handleMessageSent(pick.id)}
                    />
                  </div>
                )}
              </AccordionContent>
            </AccordionItem>
          </Accordion>
        </div>
      ))}
      {show && selectedQuestion && selectedAlarm !== null && (
        <AlarmCheckModal
          setShow={setShow}
          question={selectedQuestion}
          alarm={selectedAlarm}
        />
      )}
      <div className="text-gray-600 text-center my-10">
        받은 <span className="luckiest_guy mr-1">PICK</span>조회가
        완료되었습니다.
      </div>
    </div>
  );
};

export default Response;
