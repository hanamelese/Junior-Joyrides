import { Injectable, InternalServerErrorException, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { AddInvitationDto } from 'src/DTO/add-invitation.dto';
import { UpdateInvitationDto } from 'src/DTO/update-invitation.dto';
import { InvitationEntity, InvitationStatus } from 'src/Entity/invitation.entity';
import { Repository } from 'typeorm';

@Injectable()
export class InvitationService {
    constructor(@InjectRepository(InvitationEntity) private repo: Repository<InvitationEntity>) {}

    async getAllInvitations() {
        return await this.repo.find();
    }

    async getInvitationById(id: number) {
        const invitation = await this.repo.findOne({ where: { id } });
        if (!invitation) throw new NotFoundException(`Invitation with ID ${id} not found`);
        return invitation;
    }

    async addInvitation(userId: number, addInvitationDTO: AddInvitationDto) {
        const invitation: InvitationEntity = new InvitationEntity();
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
        } catch (err) {
            throw new InternalServerErrorException(`Something went wrong, item not created. ${err.message}`);
        }
    }

    async updateInvitation(id: number, updateInvitationDto: UpdateInvitationDto) {
        await this.repo.update({ id }, updateInvitationDto);
        return this.repo.findOne({ where: { id } });
    }

    async deleteInvitation(id: number) {
        try {
            return await this.repo.delete({ id });
        } catch (err) {
            throw new InternalServerErrorException('Something went wrong');
        }
    }
}
