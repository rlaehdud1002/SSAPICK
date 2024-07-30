import { useState } from "react";
interface ToPlusIconProps {
  campus: string;
  th: number;
  classNum: number;
  name: string;

}

const ToPlusIcon = ({ th, classNum, name }: ToPlusIconProps) => {
  const [isPlus, setIsPlus] = useState<boolean>(false);
  const doClick = () => {
    isPlus ? setIsPlus(false) : setIsPlus(true);
  }

  return (
    <div>
      <div className="mx-5 mt-5 mb-2" style={{ height: '60px', width: '60px' }}>
        <div className="relative">
          <img width={60} height={60} src="../icons/Profile.png" alt="" />
          {isPlus ? (
            <svg onClick={doClick} className="absolute bottom-1 -right-1 " width="16" height="16" viewBox="0 0 12 11" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M4.19743 9.63369L1.22243 6.65869C0.973344 6.40953 0.833441 6.07163 0.833496 5.71932C0.833551 5.36701 0.973558 5.02915 1.22272 4.78007C1.47188 4.53099 1.80978 4.39108 2.16209 4.39114C2.5144 4.39119 2.85226 4.5312 3.10134 4.78036L4.99426 6.67269L8.53859 1.94769C8.75 1.66589 9.0647 1.47961 9.41346 1.42983C9.76221 1.38006 10.1165 1.47087 10.3983 1.68228C10.6801 1.89369 10.8663 2.20838 10.9161 2.55714C10.9659 2.90589 10.8751 3.26014 10.6637 3.54194L6.20001 9.49194C6.08585 9.64436 5.94024 9.77043 5.77307 9.86161C5.60589 9.95279 5.42106 10.0069 5.23111 10.0204C5.04116 10.0338 4.85054 10.0063 4.67218 9.93953C4.49383 9.87281 4.33191 9.76851 4.19743 9.63369Z" fill="#54CC76" stroke="#54CC76" stroke-width="0.875" stroke-linejoin="round" />
            </svg>
          ) : <svg onClick={doClick} className="absolute bottom-1 -right-1" width="16" height="16" viewBox="0 0 13 13" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M11.028 4.55539H8.69465V2.22206C8.69465 1.8095 8.53076 1.41384 8.23904 1.12212C7.94731 0.830392 7.55165 0.666504 7.13909 0.666504H6.36131C5.94876 0.666504 5.55309 0.830392 5.26137 1.12212C4.96965 1.41384 4.80576 1.8095 4.80576 2.22206V4.55539H2.47243C2.05987 4.55539 1.66421 4.71928 1.37248 5.011C1.08076 5.30273 0.91687 5.69839 0.91687 6.11095V6.88873C0.91687 7.30129 1.08076 7.69695 1.37248 7.98867C1.66421 8.28039 2.05987 8.44428 2.47243 8.44428H4.80576V10.7776C4.80576 11.1902 4.96965 11.5858 5.26137 11.8776C5.55309 12.1693 5.94876 12.3332 6.36131 12.3332H7.13909C7.55165 12.3332 7.94731 12.1693 8.23904 11.8776C8.53076 11.5858 8.69465 11.1902 8.69465 10.7776V8.44428H11.028C11.4405 8.44428 11.8362 8.28039 12.1279 7.98867C12.4196 7.69695 12.5835 7.30129 12.5835 6.88873V6.11095C12.5835 5.69839 12.4196 5.30273 12.1279 5.011C11.8362 4.71928 11.4405 4.55539 11.028 4.55539Z" fill="#5F86E9" stroke="#5F86E9" stroke-width="0.7875" stroke-linecap="round" stroke-linejoin="round" />
          </svg>}
        </div>
      </div>
      <div className="flex flex-col items-center">
        <span className="text-xs text-gray-700">{th}기 {classNum}반 </span>
        <span className="text-xs text-gray-700">{name}</span>
      </div>
    </div>

  )
}

export default ToPlusIcon;  