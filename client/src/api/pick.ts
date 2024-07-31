import instance from "api/client"
import { PickAtom } from "atoms/Pick.type";

export const getReceivePick = async (): Promise<PickAtom[]> => {
  const response = await instance.get<PickAtom[]>("/pick/receive");
  return response.data;
}