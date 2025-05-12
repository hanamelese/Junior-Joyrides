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
Object.defineProperty(exports, "__esModule", { value: true });
exports.UserEntity = void 0;
const typeorm_1 = require("typeorm");
const invitation_entity_1 = require("./invitation.entity");
const wishlist_entity_1 = require("./wishlist.entity");
const basicInterview_entity_1 = require("./basicInterview.entity");
const specialInterview_entity_1 = require("./specialInterview.entity");
let UserEntity = class UserEntity {
};
exports.UserEntity = UserEntity;
__decorate([
    (0, typeorm_1.PrimaryGeneratedColumn)(),
    __metadata("design:type", Number)
], UserEntity.prototype, "id", void 0);
__decorate([
    (0, typeorm_1.Column)(),
    __metadata("design:type", String)
], UserEntity.prototype, "firstName", void 0);
__decorate([
    (0, typeorm_1.Column)(),
    __metadata("design:type", String)
], UserEntity.prototype, "lastName", void 0);
__decorate([
    (0, typeorm_1.Column)(),
    __metadata("design:type", String)
], UserEntity.prototype, "email", void 0);
__decorate([
    (0, typeorm_1.Column)(),
    __metadata("design:type", String)
], UserEntity.prototype, "password", void 0);
__decorate([
    (0, typeorm_1.Column)(),
    __metadata("design:type", String)
], UserEntity.prototype, "salt", void 0);
__decorate([
    (0, typeorm_1.Column)(),
    __metadata("design:type", String)
], UserEntity.prototype, "profileImageUrl", void 0);
__decorate([
    (0, typeorm_1.Column)(),
    __metadata("design:type", String)
], UserEntity.prototype, "backgroundImageUrl", void 0);
__decorate([
    (0, typeorm_1.Column)({ default: "user" }),
    __metadata("design:type", String)
], UserEntity.prototype, "role", void 0);
__decorate([
    (0, typeorm_1.OneToMany)(() => invitation_entity_1.InvitationEntity, invitation => invitation.user),
    __metadata("design:type", Array)
], UserEntity.prototype, "invitations", void 0);
__decorate([
    (0, typeorm_1.OneToMany)(() => basicInterview_entity_1.BasicInterviewEntity, basicInterview => basicInterview.user),
    __metadata("design:type", Array)
], UserEntity.prototype, "basicInterviews", void 0);
__decorate([
    (0, typeorm_1.OneToMany)(() => specialInterview_entity_1.SpecialInterviewEntity, specialInterview => specialInterview.user),
    __metadata("design:type", Array)
], UserEntity.prototype, "specialInterviews", void 0);
__decorate([
    (0, typeorm_1.OneToMany)(() => wishlist_entity_1.WishListEntity, wishList => wishList.user),
    __metadata("design:type", Array)
], UserEntity.prototype, "wishLists", void 0);
exports.UserEntity = UserEntity = __decorate([
    (0, typeorm_1.Entity)()
], UserEntity);
//# sourceMappingURL=user.entity.js.map