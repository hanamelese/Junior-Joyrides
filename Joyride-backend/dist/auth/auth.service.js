"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var __param = (this && this.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.AuthService = void 0;
const common_1 = require("@nestjs/common");
const typeorm_1 = require("@nestjs/typeorm");
const user_entity_1 = require("../Entity/user.entity");
const typeorm_2 = require("typeorm");
const bcrypt = require("bcryptjs");
const jwt_1 = require("@nestjs/jwt");
let AuthService = class AuthService {
    constructor(repo, jwt) {
        this.repo = repo;
        this.jwt = jwt;
    }
    async registerUser(registerDTO) {
        const { firstName, lastName, email, password } = registerDTO;
        const hashed = await bcrypt.hash(password, 12);
        const salt = await bcrypt.getSalt(hashed);
        const user = new user_entity_1.UserEntity();
        user.firstName = firstName;
        user.lastName = lastName;
        user.email = email;
        user.password = hashed;
        user.salt = salt;
        user.role = "user";
        user.profileImageUrl = '';
        user.backgroundImageUrl = '';
        this.repo.create(user);
        try {
            await this.repo.save(user);
            const jwtPayload = { email };
            const jwtToken = await this.jwt.signAsync(jwtPayload, { expiresIn: '1d', algorithm: 'HS512' });
            return { token: jwtToken };
        }
        catch (err) {
            throw new common_1.InternalServerErrorException(err);
        }
    }
    async loginUser(userLoginDTO) {
        const { email, password } = userLoginDTO;
        const user = await this.repo.findOne({ where: { email } });
        if (!user) {
            throw new common_1.UnauthorizedException('Invalid credentials.');
        }
        const isPasswordCorrect = await bcrypt.compare(password, user.password);
        if (isPasswordCorrect) {
            const jwtPayload = { email };
            const jwtToken = await this.jwt.signAsync(jwtPayload, { expiresIn: '1d', algorithm: 'HS512' });
            return { token: jwtToken };
        }
        else {
            throw new common_1.UnauthorizedException('Invalid credentials.');
        }
    }
    async getMyProfile(email) {
        try {
            const user = await this.repo.findOne({
                where: { email },
                relations: ['invitations', 'basicInterviews', 'specialInterviews', 'wishLists']
            });
            if (!user)
                throw new common_1.NotFoundException(`User with email ${email} not found`);
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
        }
        catch (err) {
            throw new common_1.InternalServerErrorException('Failed to fetch user data: ' + err.message);
        }
    }
    async updateProfile(email, updateProfileDTO) {
        if (updateProfileDTO.password) {
            const hashed = await bcrypt.hash(updateProfileDTO.password, 12);
            const salt = await bcrypt.getSalt(hashed);
            updateProfileDTO.password = hashed;
            updateProfileDTO.salt = salt;
        }
        if (updateProfileDTO.profileImageUrl && !this.isValidUrl(updateProfileDTO.profileImageUrl)) {
            throw new common_1.BadRequestException('Invalid profile image URL');
        }
        if (updateProfileDTO.backgroundImageUrl && !this.isValidUrl(updateProfileDTO.backgroundImageUrl)) {
            throw new common_1.BadRequestException('Invalid background image URL');
        }
        await this.repo.update({ email }, updateProfileDTO);
        if (updateProfileDTO.email) {
            const jwtPayload = { email: updateProfileDTO.email };
            const jwtToken = await this.jwt.signAsync(jwtPayload, { expiresIn: '1d', algorithm: 'HS512' });
            return { message: 'Profile updated successfully', token: jwtToken };
        }
        return this.getMyProfile(updateProfileDTO.email || email);
    }
    isValidUrl(url) {
        try {
            new URL(url);
            return true;
        }
        catch {
            return false;
        }
    }
};
exports.AuthService = AuthService;
exports.AuthService = AuthService = __decorate([
    (0, common_1.Injectable)(),
    __param(0, (0, typeorm_1.InjectRepository)(user_entity_1.UserEntity)),
    __metadata("design:paramtypes", [typeorm_2.Repository,
        jwt_1.JwtService])
], AuthService);
//# sourceMappingURL=auth.service.js.map