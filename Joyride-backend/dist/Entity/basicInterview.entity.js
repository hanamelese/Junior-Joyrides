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
exports.BasicInterviewStatus = exports.BasicInterviewEntity = void 0;
const typeorm_1 = require("typeorm");
const user_entity_1 = require("./user.entity");
let BasicInterviewEntity = class BasicInterviewEntity {
};
exports.BasicInterviewEntity = BasicInterviewEntity;
__decorate([
    (0, typeorm_1.PrimaryGeneratedColumn)(),
    __metadata("design:type", Number)
], BasicInterviewEntity.prototype, "id", void 0);
__decorate([
    (0, typeorm_1.Column)(),
    __metadata("design:type", String)
], BasicInterviewEntity.prototype, "childName", void 0);
__decorate([
    (0, typeorm_1.Column)(),
    __metadata("design:type", String)
], BasicInterviewEntity.prototype, "guardianName", void 0);
__decorate([
    (0, typeorm_1.Column)(),
    __metadata("design:type", Number)
], BasicInterviewEntity.prototype, "guardianPhone", void 0);
__decorate([
    (0, typeorm_1.Column)(),
    __metadata("design:type", Number)
], BasicInterviewEntity.prototype, "age", void 0);
__decorate([
    (0, typeorm_1.Column)(),
    __metadata("design:type", String)
], BasicInterviewEntity.prototype, "guardianEmail", void 0);
__decorate([
    (0, typeorm_1.Column)(),
    __metadata("design:type", String)
], BasicInterviewEntity.prototype, "specialRequests", void 0);
__decorate([
    (0, typeorm_1.Column)(),
    __metadata("design:type", Boolean)
], BasicInterviewEntity.prototype, "upcoming", void 0);
__decorate([
    (0, typeorm_1.Column)(),
    __metadata("design:type", Boolean)
], BasicInterviewEntity.prototype, "approved", void 0);
__decorate([
    (0, typeorm_1.Column)(),
    __metadata("design:type", Number)
], BasicInterviewEntity.prototype, "userId", void 0);
__decorate([
    (0, typeorm_1.ManyToOne)(() => user_entity_1.UserEntity, user => user.basicInterviews),
    (0, typeorm_1.JoinColumn)({ name: 'userId' }),
    __metadata("design:type", user_entity_1.UserEntity)
], BasicInterviewEntity.prototype, "user", void 0);
exports.BasicInterviewEntity = BasicInterviewEntity = __decorate([
    (0, typeorm_1.Entity)()
], BasicInterviewEntity);
var BasicInterviewStatus;
(function (BasicInterviewStatus) {
    BasicInterviewStatus["upComing"] = "UPCOMING";
    BasicInterviewStatus["hosted"] = "HOSTED";
})(BasicInterviewStatus || (exports.BasicInterviewStatus = BasicInterviewStatus = {}));
//# sourceMappingURL=basicInterview.entity.js.map