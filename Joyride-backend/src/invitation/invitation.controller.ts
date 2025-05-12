import { Body, Controller, Delete, Get, Param, Patch, Post, ValidationPipe, UseGuards } from '@nestjs/common';
import { InvitationService } from './invitation.service';
import { AddInvitationDto } from 'src/DTO/add-invitation.dto';
import { UpdateInvitationDto } from 'src/DTO/update-invitation.dto';
import { AuthGuard } from '@nestjs/passport';
import { UserEntity } from 'src/Entity/user.entity';
import { User } from 'src/auth/user.decorator';

@Controller('api/invitation')
export class InvitationController {
    constructor(private invitationService: InvitationService) {}

    @Get()
    getAllInvitations() {
        return this.invitationService.getAllInvitations();
    }

    @Get(':id')
    getInvitationById(
        @Param('id') id: number
    ) {
        return this.invitationService.getInvitationById(id);
    }

    @Post()
    @UseGuards(AuthGuard('jwt'))
    addInvitation(@User() user: UserEntity, @Body(ValidationPipe) data: AddInvitationDto) {
        return this.invitationService.addInvitation(user.id, data);
    }

    @Patch(':id')
    updateInvitation(
        @Param('id') id: number,
        @Body(new ValidationPipe()) updateInvitationDto: UpdateInvitationDto
    ) {
        return this.invitationService.updateInvitation(id, updateInvitationDto);
    }

    @Delete(':id')
    deleteInvitation(
        @Param('id') id: number
    ) {
        return this.invitationService.deleteInvitation(id);
    }
}
