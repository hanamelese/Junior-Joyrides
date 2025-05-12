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
exports.SpecialInterviewService = void 0;
const common_1 = require("@nestjs/common");
const typeorm_1 = require("@nestjs/typeorm");
const specialInterview_entity_1 = require("../Entity/specialInterview.entity");
const typeorm_2 = require("typeorm");
let SpecialInterviewService = class SpecialInterviewService {
    constructor(repo) {
        this.repo = repo;
    }
    async getAllSpecialInterviews() {
        return await this.repo.find();
    }
    async getSpecialInterviewById(id) {
        const specialInterview = await this.repo.findOne({ where: { id } });
        if (!specialInterview)
            throw new common_1.NotFoundException(`Special Interview with ID ${id} not found`);
        return specialInterview;
    }
    async addSpecialInterview(userId, addSpecialInterviewDTO) {
        const specialInterview = new specialInterview_entity_1.SpecialInterviewEntity();
        const { childName, guardianName, age, videoUrl, guardianPhone, guardianEmail, specialRequests } = addSpecialInterviewDTO;
        specialInterview.childName = childName;
        specialInterview.age = age;
        specialInterview.videoUrl = videoUrl;
        specialInterview.guardianName = guardianName;
        specialInterview.guardianPhone = guardianPhone;
        specialInterview.guardianEmail = guardianEmail;
        specialInterview.specialRequests = specialRequests;
        specialInterview.upcoming = true;
        specialInterview.approved = false;
        specialInterview.userId = userId;
        this.repo.create(specialInterview);
        try {
            return await this.repo.save(specialInterview);
        }
        catch (err) {
            throw new common_1.InternalServerErrorException(`Something went wrong, item not created. ${err.message}`);
        }
    }
    async updateSpecialInterview(id, updateSpecialInterviewDto) {
        await this.repo.update({ id }, updateSpecialInterviewDto);
        return this.repo.findOne({ where: { id } });
    }
    async deleteSpecialInterview(id) {
        try {
            return await this.repo.delete({ id });
        }
        catch (err) {
            throw new common_1.InternalServerErrorException('Something went wrong');
        }
    }
};
exports.SpecialInterviewService = SpecialInterviewService;
exports.SpecialInterviewService = SpecialInterviewService = __decorate([
    (0, common_1.Injectable)(),
    __param(0, (0, typeorm_1.InjectRepository)(specialInterview_entity_1.SpecialInterviewEntity)),
    __metadata("design:paramtypes", [typeorm_2.Repository])
], SpecialInterviewService);
//# sourceMappingURL=specialInterview.service.js.map