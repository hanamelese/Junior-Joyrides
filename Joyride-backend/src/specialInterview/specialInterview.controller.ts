import { Body, Controller, Delete, Get, Param, Patch, Post, ValidationPipe, UseGuards } from '@nestjs/common';
import { AuthGuard } from '@nestjs/passport';
import { UserEntity } from 'src/Entity/user.entity';
import { SpecialInterviewService } from './specialInterview.service';
import { AddSpecialInterviewDto } from 'src/DTO/add-specialInterview.dto';
import { UpdateSpecialInterviewDto } from 'src/DTO/update-specialInterview.dto';
import { User } from 'src/auth/user.decorator';

@Controller('api/special-interview')
export class SpecialInterviewController {
    constructor(private specialInterviewService: SpecialInterviewService) {}

    @Get()
    getAllSpecialInterviews() {
        return this.specialInterviewService.getAllSpecialInterviews();
    }

    @Get(':id')
    getSpecialInterviewById(
        @Param('id') id: number
    ) {
        return this.specialInterviewService.getSpecialInterviewById(id);
    }

    @Post()
    @UseGuards(AuthGuard('jwt'))
    addSpecialInterview(@User() user: UserEntity, @Body(ValidationPipe) data: AddSpecialInterviewDto) {
        return this.specialInterviewService.addSpecialInterview(user.id, data);
    }

    @Patch(':id')
    updateSpecialInterview(
        @Param('id') id: number,
        @Body(new ValidationPipe()) updateSpecialInterviewDto: UpdateSpecialInterviewDto
    ) {
        return this.specialInterviewService.updateSpecialInterview(id, updateSpecialInterviewDto);
    }

    @Delete(':id')
    deleteSpecialInterview(
        @Param('id') id: number
    ) {
        return this.specialInterviewService.deleteSpecialInterview(id);
    }
}
