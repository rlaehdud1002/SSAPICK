import UserAddInfo from "components/LoginPage/UserAddInfo";
import UserInfo from "components/LoginPage/UserInfo";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "components/ui/tabs";
import { useState } from "react";

const InfoInsert = () => {
  const [tab, setTab] = useState<string>("userinfo");

  return (
    <div>
      <Tabs
        onValueChange={(value) => setTab(value)}
        value={tab}
        defaultValue="userinfo"
        className="w-[400px]"
      >
        <TabsContent value="userinfo">
          <UserInfo next={() => setTab("useraddinfo")} />
        </TabsContent>
        <TabsContent value="useraddinfo">
          <UserAddInfo />
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
};

export default InfoInsert;
