import { AuthService } from "./auth.service";
import { UpdateProfileDTO } from "src/DTO/update-profile.dto";
import { UserEntity } from "src/Entity/user.entity";
export declare class UserController {
    private readonly authService;
    constructor(authService: AuthService);
    getMyProfile(user: UserEntity): Promise<{
        id: number;
        firstName: string;
        lastName: string;
        email: string;
        role: string;
        profileImageUrl: string;
        backgroundImageUrl: string;
        invitations: import("../Entity/invitation.entity").InvitationEntity[];
        basicInterviews: import("../Entity/basicInterview.entity").BasicInterviewEntity[];
        specialInterviews: import("../Entity/specialInterview.entity").SpecialInterviewEntity[];
        wishLists: import("../Entity/wishlist.entity").WishListEntity[];
    }>;
    updateProfile(user: UserEntity, updateProfileDTO: UpdateProfileDTO): Promise<{
        id: number;
        firstName: string;
        lastName: string;
        email: string;
        role: string;
        profileImageUrl: string;
        backgroundImageUrl: string;
        invitations: import("../Entity/invitation.entity").InvitationEntity[];
        basicInterviews: import("../Entity/basicInterview.entity").BasicInterviewEntity[];
        specialInterviews: import("../Entity/specialInterview.entity").SpecialInterviewEntity[];
        wishLists: import("../Entity/wishlist.entity").WishListEntity[];
    } | {
        message: string;
        token: string;
    }>;
}
