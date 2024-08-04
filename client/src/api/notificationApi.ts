import instance from "./clientApi";

export const registerToken = async (token: string): Promise<void> => {
  const {
    data: { success, data, message },
  } = await instance.post('/notification/register', {
    token
  });
  if (!success) {
    throw new Error(message);
  }
  return data;
};