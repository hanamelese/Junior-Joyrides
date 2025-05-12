import { BadRequestException, Injectable, InternalServerErrorException, NotFoundException, UnauthorizedException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { RegisterUserDTO } from 'src/DTO/register-user.dto';
import { UserEntity } from 'src/Entity/user.entity';
import { Repository } from 'typeorm';
import * as bcrypt from 'bcryptjs';
import { UserLoginDTO } from 'src/DTO/user-login.dto';
import { JwtService } from '@nestjs/jwt';
import { UpdateProfileDTO } from 'src/DTO/update-profile.dto';

@Injectable()
export class AuthService {
    constructor(
        @InjectRepository(UserEntity) private repo: Repository<UserEntity>,
        private jwt: JwtService
    ) {}

    async registerUser(registerDTO: RegisterUserDTO) {
        const { firstName, lastName, email, password } = registerDTO;
        const hashed = await bcrypt.hash(password, 12);
        const salt = await bcrypt.getSalt(hashed);

        const user = new UserEntity();
        user.firstName = firstName;
        user.lastName = lastName;
        user.email = email;
        user.password = hashed;
        user.salt = salt;
        user.role = "user"; // Set default role
        user.profileImageUrl =''; 
        user.backgroundImageUrl ='';

        this.repo.create(user);
        try {
            await this.repo.save(user);
            const jwtPayload = { email };
            const jwtToken = await this.jwt.signAsync(jwtPayload, { expiresIn: '1d', algorithm: 'HS512' });
            return { token: jwtToken };
        } catch (err) {
            throw new InternalServerErrorException(err);
        }
    }

    async loginUser(userLoginDTO: UserLoginDTO) {
        const { email, password } = userLoginDTO;

        const user = await this.repo.findOne({ where: { email } });

        if (!user) {
            throw new UnauthorizedException('Invalid credentials.');
        }

        const isPasswordCorrect = await bcrypt.compare(password, user.password);
        if (isPasswordCorrect) {
            const jwtPayload = { email };
            const jwtToken = await this.jwt.signAsync(jwtPayload, { expiresIn: '1d', algorithm: 'HS512' });
            return { token: jwtToken };
        } else {
            throw new UnauthorizedException('Invalid credentials.');
        }
    }

    async getMyProfile(email: string) {
        try {
            const user = await this.repo.findOne({
                where: { email },
                relations: ['invitations', 'basicInterviews', 'specialInterviews', 'wishLists']
            });
            if (!user) throw new NotFoundException(`User with email ${email} not found`);
            return {
                id: user.id,
                firstName: user.firstName,
                lastName: user.lastName,
                email: user.email,
                role: user.role,
                profileImageUrl: user.profileImageUrl,
                backgroundImageUrl: user.backgroundImageUrl,
                invitations: user.invitations || [],
                basicInterviews: user.basicInterviews || [],
                specialInterviews: user.specialInterviews || [],
                wishLists: user.wishLists || []
            };
        } catch (err) {
            throw new InternalServerErrorException('Failed to fetch user data: ' + err.message);
        }
    }

    async updateProfile(email: string, updateProfileDTO: UpdateProfileDTO) {
        if (updateProfileDTO.password) {
            const hashed = await bcrypt.hash(updateProfileDTO.password, 12);
            const salt = await bcrypt.getSalt(hashed);
            updateProfileDTO.password = hashed;
            updateProfileDTO.salt = salt;
        }
        // Validate URLs if provided
        if (updateProfileDTO.profileImageUrl && !this.isValidUrl(updateProfileDTO.profileImageUrl)) {
            throw new BadRequestException('Invalid profile image URL');
        }
        if (updateProfileDTO.backgroundImageUrl && !this.isValidUrl(updateProfileDTO.backgroundImageUrl)) {
            throw new BadRequestException('Invalid background image URL');
        }
        await this.repo.update({ email }, updateProfileDTO);

        if (updateProfileDTO.email) {
            const jwtPayload = { email: updateProfileDTO.email };
            const jwtToken = await this.jwt.signAsync(jwtPayload, { expiresIn: '1d', algorithm: 'HS512' });
            return { message: 'Profile updated successfully', token: jwtToken };
        }

        return this.getMyProfile(updateProfileDTO.email || email);
    }

    private isValidUrl(url: string): boolean {
        try {
            new URL(url);
            return true;
        } catch {
            return false;
        }
    }
}
