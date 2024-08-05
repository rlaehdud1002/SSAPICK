
import DoneButton from "buttons/DoneButton";
import UserAddInfo from "components/LoginPage/UserAddInfo";
import UserInfo from "components/LoginPage/UserInfo";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "components/ui/tabs";
import { useState } from "react";
import { FieldValues, SubmitHandler, useForm } from "react-hook-form";

const InfoInsert = () => {
  const [tab, setTab] = useState<string>("userinfo");
  
  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault(); // 폼 제출 기본 동작 방지
    if (tab === "userinfo") {
      setTab("useraddinfo");
    } else {
      // 완료 버튼 클릭 시의 로직을 여기에 추가
      console.log("완료");
    }
  };
  return (
    <form >
      <Tabs onValueChange={(value) => setTab(value)} value={tab} defaultValue="userinfo" className="w-[400px]">
        <TabsContent value="userinfo">
          <UserInfo />
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
      {tab === "useraddinfo" ?(
      <div className="flex justify-center">
      <DoneButton title="완료" />
      </div>):(<div onClick={()=>setTab("useraddinfo")} className="flex justify-center">
      <DoneButton title="다음" />
      </div>)
      }

      </Tabs>
      
    </form>
  )
}

export default InfoInsert;