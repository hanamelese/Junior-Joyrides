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
exports.InvitationService = void 0;
const common_1 = require("@nestjs/common");
const typeorm_1 = require("@nestjs/typeorm");
const invitation_entity_1 = require("../Entity/invitation.entity");
const typeorm_2 = require("typeorm");
let InvitationService = class InvitationService {
    constructor(repo) {
        this.repo = repo;
    }
    async getAllInvitations() {
        return await this.repo.find();
    }
    async getInvitationById(id) {
        const invitation = await this.repo.findOne({ where: { id } });
        if (!invitation)
            throw new common_1.NotFoundException(`Invitation with ID ${id} not found`);
        return invitation;
    }
    async addInvitation(userId, addInvitationDTO) {
        const invitation = new invitation_entity_1.InvitationEntity();
        const { childName, age, address, guardianPhone, guardianEmail, specialRequests, date, time } = addInvitationDTO;
        invitation.childName = childName;
        invitation.age = age;
        invitation.address = address;
        invitation.guardianPhone = guardianPhone;
        invitation.guardianEmail = guardianEmail;
        invitation.specialRequests = specialRequests;
        invitation.date = date;
        invitation.time = time;
        invitation.upcoming = true;
        invitation.approved = false;
        invitation.userId = userId;
        this.repo.create(invitation);
        try {
            return await this.repo.save(invitation);
        }
        catch (err) {
            throw new common_1.InternalServerErrorException(`Something went wrong, item not created. ${err.message}`);
        }
    }
    async updateInvitation(id, updateInvitationDto) {
        await this.repo.update({ id }, updateInvitationDto);
        return this.repo.findOne({ where: { id } });
    }
    async deleteInvitation(id) {
        try {
            return await this.repo.delete({ id });
        }
        catch (err) {
            throw new common_1.InternalServerErrorException('Something went wrong');
        }
    }
};
exports.InvitationService = InvitationService;
exports.InvitationService = InvitationService = __decorate([
    (0, common_1.Injectable)(),
    __param(0, (0, typeorm_1.InjectRepository)(invitation_entity_1.InvitationEntity)),
    __metadata("design:paramtypes", [typeorm_2.Repository])
], InvitationService);
//# sourceMappingURL=invitation.service.js.map