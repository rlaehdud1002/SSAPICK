import { profile } from 'console';
export interface ILocation{
    count: number;
    locations: {
        username: string;
        position: {
            x: number;
            y: number;
        }
        profileImage: string;
        distance: number;
    }[];
}

export interface ISelectFriend{
    username:string;
}