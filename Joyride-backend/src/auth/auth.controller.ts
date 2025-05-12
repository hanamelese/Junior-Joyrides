import { Body, Controller, Post, ValidationPipe } from '@nestjs/common';
import { AuthService } from './auth.service';
import { RegisterUserDTO } from 'src/DTO/register-user.dto';
import { UserLoginDTO } from 'src/DTO/user-login.dto';

//http:localhost/3000/api/auth
@Controller('api/auth')
export class AuthController {

    constructor(private authService:AuthService){}

    @Post('register')
    registerUser(@Body(ValidationPipe) regDTO: RegisterUserDTO){
        return this.authService.registerUser(regDTO);
    }

    @Post('login')
    loginUser(@Body(ValidationPipe) loginDTO: UserLoginDTO){
        return this.authService.loginUser(loginDTO);
    }

}
