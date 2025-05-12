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
exports.SpecialInterviewController = void 0;
const common_1 = require("@nestjs/common");
const passport_1 = require("@nestjs/passport");
const user_entity_1 = require("../Entity/user.entity");
const specialInterview_service_1 = require("./specialInterview.service");
const add_specialInterview_dto_1 = require("../DTO/add-specialInterview.dto");
const update_specialInterview_dto_1 = require("../DTO/update-specialInterview.dto");
const user_decorator_1 = require("../auth/user.decorator");
let SpecialInterviewController = class SpecialInterviewController {
    constructor(specialInterviewService) {
        this.specialInterviewService = specialInterviewService;
    }
    getAllSpecialInterviews() {
        return this.specialInterviewService.getAllSpecialInterviews();
    }
    getSpecialInterviewById(id) {
        return this.specialInterviewService.getSpecialInterviewById(id);
    }
    addSpecialInterview(user, data) {
        return this.specialInterviewService.addSpecialInterview(user.id, data);
    }
    updateSpecialInterview(id, updateSpecialInterviewDto) {
        return this.specialInterviewService.updateSpecialInterview(id, updateSpecialInterviewDto);
    }
    deleteSpecialInterview(id) {
        return this.specialInterviewService.deleteSpecialInterview(id);
    }
};
exports.SpecialInterviewController = SpecialInterviewController;
__decorate([
    (0, common_1.Get)(),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", []),
    __metadata("design:returntype", void 0)
], SpecialInterviewController.prototype, "getAllSpecialInterviews", null);
__decorate([
    (0, common_1.Get)(':id'),
    __param(0, (0, common_1.Param)('id')),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Number]),
    __metadata("design:returntype", void 0)
], SpecialInterviewController.prototype, "getSpecialInterviewById", null);
__decorate([
    (0, common_1.Post)(),
    (0, common_1.UseGuards)((0, passport_1.AuthGuard)('jwt')),
    __param(0, (0, user_decorator_1.User)()),
    __param(1, (0, common_1.Body)(common_1.ValidationPipe)),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [user_entity_1.UserEntity, add_specialInterview_dto_1.AddSpecialInterviewDto]),
    __metadata("design:returntype", void 0)
], SpecialInterviewController.prototype, "addSpecialInterview", null);
__decorate([
    (0, common_1.Patch)(':id'),
    __param(0, (0, common_1.Param)('id')),
    __param(1, (0, common_1.Body)(new common_1.ValidationPipe())),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Number, update_specialInterview_dto_1.UpdateSpecialInterviewDto]),
    __metadata("design:returntype", void 0)
], SpecialInterviewController.prototype, "updateSpecialInterview", null);
__decorate([
    (0, common_1.Delete)(':id'),
    __param(0, (0, common_1.Param)('id')),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Number]),
    __metadata("design:returntype", void 0)
], SpecialInterviewController.prototype, "deleteSpecialInterview", null);
exports.SpecialInterviewController = SpecialInterviewController = __decorate([
    (0, common_1.Controller)('api/special-interview'),
    __metadata("design:paramtypes", [specialInterview_service_1.SpecialInterviewService])
], SpecialInterviewController);
//# sourceMappingURL=specialInterview.controller.js.map