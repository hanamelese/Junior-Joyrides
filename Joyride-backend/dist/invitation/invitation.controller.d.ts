import { InvitationService } from './invitation.service';
import { AddInvitationDto } from 'src/DTO/add-invitation.dto';
import { UpdateInvitationDto } from 'src/DTO/update-invitation.dto';
import { UserEntity } from 'src/Entity/user.entity';
export declare class InvitationController {
    private invitationService;
    constructor(invitationService: InvitationService);
    getAllInvitations(): Promise<import("../Entity/invitation.entity").InvitationEntity[]>;
    getInvitationById(id: number): Promise<import("../Entity/invitation.entity").InvitationEntity>;
    addInvitation(user: UserEntity, data: AddInvitationDto): Promise<import("../Entity/invitation.entity").InvitationEntity>;
    updateInvitation(id: number, updateInvitationDto: UpdateInvitationDto): Promise<import("../Entity/invitation.entity").InvitationEntity>;
    deleteInvitation(id: number): Promise<import("typeorm").DeleteResult>;
}
