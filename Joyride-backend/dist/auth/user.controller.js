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
exports.UserController = void 0;
const common_1 = require("@nestjs/common");
const auth_service_1 = require("./auth.service");
const passport_1 = require("@nestjs/passport");
const update_profile_dto_1 = require("../DTO/update-profile.dto");
const user_entity_1 = require("../Entity/user.entity");
const user_decorator_1 = require("./user.decorator");
let UserController = class UserController {
    constructor(authService) {
        this.authService = authService;
    }
    getMyProfile(user) {
        return this.authService.getMyProfile(user.email);
    }
    async updateProfile(user, updateProfileDTO) {
        return this.authService.updateProfile(user.email, updateProfileDTO);
    }
};
exports.UserController = UserController;
__decorate([
    (0, common_1.Get)('my-profile'),
    __param(0, (0, user_decorator_1.User)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [user_entity_1.UserEntity]),
    __metadata("design:returntype", void 0)
], UserController.prototype, "getMyProfile", null);
__decorate([
    (0, common_1.Patch)('edit-profile'),
    __param(0, (0, user_decorator_1.User)()),
    __param(1, (0, common_1.Body)(new common_1.ValidationPipe())),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [user_entity_1.UserEntity,
        update_profile_dto_1.UpdateProfileDTO]),
    __metadata("design:returntype", Promise)
], UserController.prototype, "updateProfile", null);
exports.UserController = UserController = __decorate([
    (0, common_1.UseGuards)((0, passport_1.AuthGuard)('jwt')),
    (0, common_1.Controller)('api/user'),
    __metadata("design:paramtypes", [auth_service_1.AuthService])
], UserController);
//# sourceMappingURL=user.controller.js.map