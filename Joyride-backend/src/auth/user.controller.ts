import { Body, Controller, Get, Patch, UseGuards, ValidationPipe } from "@nestjs/common";
import { AuthService } from "./auth.service";
import { AuthGuard } from "@nestjs/passport";
import { UpdateProfileDTO } from "src/DTO/update-profile.dto";
import { UserEntity } from "src/Entity/user.entity";
import { User } from "./user.decorator";

@UseGuards(AuthGuard('jwt'))
@Controller('api/user')
export class UserController {
    constructor(private readonly authService: AuthService) {}

    @Get('my-profile')
    getMyProfile(@User() user: UserEntity) {
        return this.authService.getMyProfile(user.email);
    }

    @Patch('edit-profile')
    async updateProfile(
        @User() user: UserEntity,
        @Body(new ValidationPipe()) updateProfileDTO: UpdateProfileDTO
    ) {
        return this.authService.updateProfile(user.email, updateProfileDTO);
    }
}

