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
exports.BasicInterviewService = void 0;
const common_1 = require("@nestjs/common");
const typeorm_1 = require("@nestjs/typeorm");
const basicInterview_entity_1 = require("../Entity/basicInterview.entity");
const typeorm_2 = require("typeorm");
let BasicInterviewService = class BasicInterviewService {
    constructor(repo) {
        this.repo = repo;
    }
    async getAllBasicInterviews() {
        return await this.repo.find();
    }
    async getBasicInterviewById(id) {
        const basicInterview = await this.repo.findOne({ where: { id } });
        if (!basicInterview)
            throw new common_1.NotFoundException(`Basic Interview with ID ${id} not found`);
        return basicInterview;
    }
    async addBasicInterview(userId, addBasicInterviewDTO) {
        const basicInterview = new basicInterview_entity_1.BasicInterviewEntity();
        const { childName, guardianName, age, guardianPhone, guardianEmail, specialRequests } = addBasicInterviewDTO;
        basicInterview.childName = childName;
        basicInterview.age = age;
        basicInterview.guardianName = guardianName;
        basicInterview.guardianPhone = guardianPhone;
        basicInterview.guardianEmail = guardianEmail;
        basicInterview.specialRequests = specialRequests;
        basicInterview.upcoming = true;
        basicInterview.approved = false;
        basicInterview.userId = userId;
        this.repo.create(basicInterview);
        try {
            return await this.repo.save(basicInterview);
        }
        catch (err) {
            throw new common_1.InternalServerErrorException(`Something went wrong, item not created. ${err.message}`);
        }
    }
    async updateBasicInterview(id, updateBasicInterviewDto) {
        await this.repo.update({ id }, updateBasicInterviewDto);
        return this.repo.findOne({ where: { id } });
    }
    async deleteBasicInterview(id) {
        try {
            return await this.repo.delete({ id });
        }
        catch (err) {
            throw new common_1.InternalServerErrorException('Something went wrong');
        }
    }
};
exports.BasicInterviewService = BasicInterviewService;
exports.BasicInterviewService = BasicInterviewService = __decorate([
    (0, common_1.Injectable)(),
    __param(0, (0, typeorm_1.InjectRepository)(basicInterview_entity_1.BasicInterviewEntity)),
    __metadata("design:paramtypes", [typeorm_2.Repository])
], BasicInterviewService);
//# sourceMappingURL=basicInterview.service.js.map