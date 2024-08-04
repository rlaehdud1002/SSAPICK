import { atom, selector } from "recoil";

export const validState = atom({
  key: "validState",
  default: {
    lockedUser: true,
    validInfo: false,
    mattermostConfirmed: false,
  },
});

// export const isValidateState = selector({
//   key: "isValidateState",
//   get: ({ get }) => {
//     const valid = get(validState);
//     return valid.lockedUser && valid.validInfo && valid.mattermostConfirmed;
//   },
// });
