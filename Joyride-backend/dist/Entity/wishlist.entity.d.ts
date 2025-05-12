import { UserEntity } from "./user.entity";
export declare class WishListEntity {
    id: number;
    childName: string;
    guardianEmail: string;
    age: number;
    date: string;
    specialRequests: string;
    imageUrl: string;
    upcoming: boolean;
    approved: boolean;
    userId: number;
    user: UserEntity;
}
export declare enum WishListStatus {
    upComing = "UPCOMING",
    posted = "POSTED"
}
