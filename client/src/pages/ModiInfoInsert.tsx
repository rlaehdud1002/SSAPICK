import ModiUserAddInfo from "components/LoginPage/ModiUserAddInfo";
import ModiUserInfo from "components/LoginPage/ModiUserInfo";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "components/ui/tabs";
import { useState } from "react";

const ModiInfoInsert = () => {
  const [tab, setTab] = useState<string>("userinfo");
  return (
    <div className="mt-5">
      {/* <span className="ml-7">정보 수정</span> */}
      <Tabs
        onValueChange={(value) => setTab(value)}
        value={tab}
        defaultValue="userinfo"
        className="w-[400px]"
      >
        <TabsContent value="userinfo">
          <ModiUserInfo next={() => setTab("useraddinfo")} />
        </TabsContent>
        <TabsContent value="useraddinfo">
          <ModiUserAddInfo />
        </TabsContent>
        <div className="flex justify-center">
          <TabsList>
            <div className="mx-1">
              <TabsTrigger value="userinfo"></TabsTrigger>
            </div>
            <div className="mx-1">
              <TabsTrigger value="useraddinfo"></TabsTrigger>
            </div>
          </TabsList>
        </div>
      </Tabs>
    </div>
  );
}

export default ModiInfoInsert