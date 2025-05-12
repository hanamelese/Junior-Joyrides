import { AddInvitationDto } from 'src/DTO/add-invitation.dto';
import { UpdateInvitationDto } from 'src/DTO/update-invitation.dto';
import { InvitationEntity } from 'src/Entity/invitation.entity';
import { Repository } from 'typeorm';
export declare class InvitationService {
    private repo;
    constructor(repo: Repository<InvitationEntity>);
    getAllInvitations(): Promise<InvitationEntity[]>;
    getInvitationById(id: number): Promise<InvitationEntity>;
    addInvitation(userId: number, addInvitationDTO: AddInvitationDto): Promise<InvitationEntity>;
    updateInvitation(id: number, updateInvitationDto: UpdateInvitationDto): Promise<InvitationEntity>;
    deleteInvitation(id: number): Promise<import("typeorm").DeleteResult>;
}
