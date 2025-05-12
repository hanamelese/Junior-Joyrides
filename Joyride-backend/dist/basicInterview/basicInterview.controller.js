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
exports.BasicInterviewController = void 0;
const common_1 = require("@nestjs/common");
const passport_1 = require("@nestjs/passport");
const user_entity_1 = require("../Entity/user.entity");
const basicInterview_service_1 = require("./basicInterview.service");
const add_basicInterview_dto_1 = require("../DTO/add-basicInterview.dto");
const user_decorator_1 = require("../auth/user.decorator");
const update_basicInterview_dto_1 = require("../DTO/update-basicInterview.dto");
let BasicInterviewController = class BasicInterviewController {
    constructor(basicInterviewService) {
        this.basicInterviewService = basicInterviewService;
    }
    getAllBasicInterviews() {
        return this.basicInterviewService.getAllBasicInterviews();
    }
    getBasicInterviewById(id) {
        return this.basicInterviewService.getBasicInterviewById(id);
    }
    addBasicInterview(user, data) {
        return this.basicInterviewService.addBasicInterview(user.id, data);
    }
    updateBasicInterview(id, updateBasicInterviewDto) {
        return this.basicInterviewService.updateBasicInterview(id, updateBasicInterviewDto);
    }
    deleteBasicInterview(id) {
        return this.basicInterviewService.deleteBasicInterview(id);
    }
};
exports.BasicInterviewController = BasicInterviewController;
__decorate([
    (0, common_1.Get)(),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", []),
    __metadata("design:returntype", void 0)
], BasicInterviewController.prototype, "getAllBasicInterviews", null);
__decorate([
    (0, common_1.Get)(':id'),
    __param(0, (0, common_1.Param)('id')),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Number]),
    __metadata("design:returntype", void 0)
], BasicInterviewController.prototype, "getBasicInterviewById", null);
__decorate([
    (0, common_1.Post)(),
    (0, common_1.UseGuards)((0, passport_1.AuthGuard)('jwt')),
    __param(0, (0, user_decorator_1.User)()),
    __param(1, (0, common_1.Body)(common_1.ValidationPipe)),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [user_entity_1.UserEntity, add_basicInterview_dto_1.AddBasicInterviewDto]),
    __metadata("design:returntype", void 0)
], BasicInterviewController.prototype, "addBasicInterview", null);
__decorate([
    (0, common_1.Patch)(':id'),
    __param(0, (0, common_1.Param)('id')),
    __param(1, (0, common_1.Body)(new common_1.ValidationPipe())),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Number, update_basicInterview_dto_1.UpdateBasicInterviewDto]),
    __metadata("design:returntype", void 0)
], BasicInterviewController.prototype, "updateBasicInterview", null);
__decorate([
    (0, common_1.Delete)(':id'),
    __param(0, (0, common_1.Param)('id')),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Number]),
    __metadata("design:returntype", void 0)
], BasicInterviewController.prototype, "deleteBasicInterview", null);
exports.BasicInterviewController = BasicInterviewController = __decorate([
    (0, common_1.Controller)('api/basic-interview'),
    __metadata("design:paramtypes", [basicInterview_service_1.BasicInterviewService])
], BasicInterviewController);
//# sourceMappingURL=basicInterview.controller.js.map