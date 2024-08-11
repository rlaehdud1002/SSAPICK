import { atom } from "recoil";
import { Ilocation } from "./Location.type";

export const LocationState = atom<Ilocation>({
    key: 'LocationState',
    default:{
    },
    });
