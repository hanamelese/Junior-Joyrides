import { UserEntity } from "./user.entity";
export declare class InvitationEntity {
    id: number;
    childName: string;
    guardianPhone: number;
    age: number;
    guardianEmail: string;
    specialRequests: string;
    address: string;
    date: string;
    time: number;
    upcoming: boolean;
    approved: boolean;
    userId: number;
    user: UserEntity;
}
export declare enum InvitationStatus {
    upComing = "UPCOMING",
    celebrated = "CELEBRATED"
}
