import { BaseResponse } from "atoms/User.type";
import instance from "./clientApi"
import { ILocation } from "atoms/Location.type";

export const findFriends = async (): Promise<ILocation> => {
    const { data: {data, success, message} } = await instance.get<BaseResponse<ILocation>>("/location");
    return data;
}