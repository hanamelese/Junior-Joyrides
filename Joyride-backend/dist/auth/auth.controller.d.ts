import { AuthService } from './auth.service';
import { RegisterUserDTO } from 'src/DTO/register-user.dto';
import { UserLoginDTO } from 'src/DTO/user-login.dto';
export declare class AuthController {
    private authService;
    constructor(authService: AuthService);
    registerUser(regDTO: RegisterUserDTO): Promise<{
        token: string;
    }>;
    loginUser(loginDTO: UserLoginDTO): Promise<{
        token: string;
    }>;
}
