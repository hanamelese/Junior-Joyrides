import { UserEntity } from "./user.entity";
export declare class BasicInterviewEntity {
    id: number;
    childName: string;
    guardianName: string;
    guardianPhone: number;
    age: number;
    guardianEmail: string;
    specialRequests: string;
    upcoming: boolean;
    approved: boolean;
    userId: number;
    user: UserEntity;
}
export declare enum BasicInterviewStatus {
    upComing = "UPCOMING",
    hosted = "HOSTED"
}
