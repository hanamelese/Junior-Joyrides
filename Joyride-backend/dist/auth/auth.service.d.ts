import { RegisterUserDTO } from 'src/DTO/register-user.dto';
import { UserEntity } from 'src/Entity/user.entity';
import { Repository } from 'typeorm';
import { UserLoginDTO } from 'src/DTO/user-login.dto';
import { JwtService } from '@nestjs/jwt';
import { UpdateProfileDTO } from 'src/DTO/update-profile.dto';
export declare class AuthService {
    private repo;
    private jwt;
    constructor(repo: Repository<UserEntity>, jwt: JwtService);
    registerUser(registerDTO: RegisterUserDTO): Promise<{
        token: string;
    }>;
    loginUser(userLoginDTO: UserLoginDTO): Promise<{
        token: string;
    }>;
    getMyProfile(email: string): Promise<{
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
    updateProfile(email: string, updateProfileDTO: UpdateProfileDTO): Promise<{
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
    private isValidUrl;
}
