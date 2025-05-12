import { InvitationEntity } from "./invitation.entity";
import { WishListEntity } from "./wishlist.entity";
import { BasicInterviewEntity } from "./basicInterview.entity";
import { SpecialInterviewEntity } from "./specialInterview.entity";
export declare class UserEntity {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    salt: string;
    profileImageUrl: string;
    backgroundImageUrl: string;
    role: string;
    invitations: InvitationEntity[];
    basicInterviews: BasicInterviewEntity[];
    specialInterviews: SpecialInterviewEntity[];
    wishLists: WishListEntity[];
}
