// import {
//   Select,
//   SelectContent,
//   SelectGroup,
//   SelectTrigger,
//   SelectValue,
//   SelectItem,
// } from 'components/ui/select';
// import Selected from 'components/Selected';
// import { useState } from 'react';
// import React from 'react';

// interface InfoDropProps {
//   title: string;
// }

// const InfoSelect = ({ title }: InfoDropProps) => {
  
//   return (
//     <Select>
//       <SelectTrigger className="w-[180px]">
//         <SelectValue placeholder={title} />
//       </SelectTrigger>
//       <SelectContent>
//         <SelectGroup>
//             <SelectItem value="남자">남자</SelectItem>
//             <SelectItem value="여자">여자</SelectItem>
//         </SelectGroup>
//       </SelectContent>
//     </Select>
//   )
// }

// export default InfoSelect;
import React, { useState } from 'react';
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectTrigger,
  SelectValue,
  SelectItem,
} from 'components/ui/select';

interface InfoSelectProps {
  title: string;
}

const InfoSelect: React.FC<InfoSelectProps> = ({ title }) => {
  const [selectedValue, setSelectedValue] = useState<string | null>(null);

  const handleChange = (value: string) => {
    setSelectedValue(value);
  };

  return (
    <Select onValueChange={handleChange}>
      <SelectTrigger className="w-[180px]">
        <SelectValue placeholder={selectedValue || title} />
      </SelectTrigger>
      <SelectContent>
        <SelectGroup>
          {title === "성별" && (
            <>
              <SelectItem value="man">남자</SelectItem>
              <SelectItem value="woman">여자</SelectItem>
            </>
          )}
          {title === "기수" && (
            <>
              <SelectItem value="11th">11기</SelectItem>
              <SelectItem value="12th">12기</SelectItem>
            </>
          )}
          {title === "캠퍼스" && (
            <>
              <SelectItem value="seoul">서울</SelectItem>
              <SelectItem value="daejeon">대전</SelectItem>
              <SelectItem value="gwangju">광주</SelectItem>
              <SelectItem value="booulgyeong">부울경</SelectItem>
              <SelectItem value="gumi">구미</SelectItem>
            </>
          )}
          {title === "반" && (
            <>
              <SelectItem value="class_1">1반</SelectItem>
              <SelectItem value="class_2">2반</SelectItem>
              <SelectItem value="class_3">3반</SelectItem>
              <SelectItem value="class_4">4반</SelectItem>
              <SelectItem value="class_5">5반</SelectItem>
              <SelectItem value="class_6">6반</SelectItem>
              <SelectItem value="class_7">7반</SelectItem>
              <SelectItem value="class_8">8반</SelectItem>
            </>
          )}
        </SelectGroup>
      </SelectContent>
    </Select>
  );
};

export default InfoSelect;
