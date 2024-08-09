import instance from "./clientApi"

export const findFriends = async () => {
    const response = await instance.get("/location");
    return response.data;
}