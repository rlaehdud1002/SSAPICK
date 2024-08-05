
import UserAddInfo from "components/LoginPage/UserAddInfo";
import UserInfo from "components/LoginPage/UserInfo";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "components/ui/tabs";
import { useRef, useState } from "react";

const InfoInsert = () => {
  const [tab, setTab] = useState<string>("userinfo");
  const infoRef = useRef<HTMLFormElement>(null);
  const addInfoRef = useRef<HTMLFormElement>(null);

  return (
    <div >
      <Tabs onValueChange={(value) => setTab(value)} value={tab} defaultValue="userinfo" className="w-[400px]">
        <TabsContent value="userinfo">
          <UserInfo next={() => setTab('useraddinfo')} />
        </TabsContent>
        <TabsContent value="useraddinfo">
          <UserAddInfo />
        </TabsContent>
        <div className="flex justify-center">
          <TabsList>
            <div className="mx-1">
              <TabsTrigger value="userinfo">

              </TabsTrigger>
            </div>
            <div className="mx-1">
              <TabsTrigger value="useraddinfo"></TabsTrigger>
            </div>
          </TabsList>
        </div>
        {/* {tab === "useraddinfo" ? (
          <div className="flex justify-center">
            <DoneButton title="완료" />
          </div>) : (<div onClick={(e) => {
            e.preventDefault();
            infoRef.current?.submit();
          }} className="flex justify-center">
            <DoneButton title="다음" />
          </div>)
        } */}

      </Tabs>

    </div>
  )
}

export default InfoInsert;