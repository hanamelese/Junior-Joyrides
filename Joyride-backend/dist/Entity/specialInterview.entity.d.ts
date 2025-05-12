import { UserEntity } from "./user.entity";
export declare class SpecialInterviewEntity {
    id: number;
    childName: string;
    guardianName: string;
    guardianPhone: number;
    age: number;
    guardianEmail: string;
    specialRequests: string;
    videoUrl: string;
    upcoming: boolean;
    approved: boolean;
    userId: number;
    user: UserEntity;
}
export declare enum SpecialInterviewStatus {
    upComing = "UPCOMING",
    hosted = "HOSTED"
}
