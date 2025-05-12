import { Body, Controller, Delete, Get, Param, Patch, Post, ValidationPipe, UseGuards } from '@nestjs/common';
import { AuthGuard } from '@nestjs/passport';
import { UserEntity } from 'src/Entity/user.entity';
import { BasicInterviewService } from './basicInterview.service';
import { AddBasicInterviewDto } from 'src/DTO/add-basicInterview.dto';
import { User } from 'src/auth/user.decorator';
import { UpdateBasicInterviewDto } from 'src/DTO/update-basicInterview.dto';

@Controller('api/basic-interview')
export class BasicInterviewController {
    constructor(private basicInterviewService: BasicInterviewService) {}

    @Get()
    getAllBasicInterviews() {
        return this.basicInterviewService.getAllBasicInterviews();
    }

    @Get(':id')
    getBasicInterviewById(
        @Param('id') id: number
    ) {
        return this.basicInterviewService.getBasicInterviewById(id);
    }

    @Post()
    @UseGuards(AuthGuard('jwt'))
    addBasicInterview(@User() user: UserEntity, @Body(ValidationPipe) data: AddBasicInterviewDto) {
        return this.basicInterviewService.addBasicInterview(user.id, data);
    }

    @Patch(':id')
    updateBasicInterview(
        @Param('id') id: number,
        @Body(new ValidationPipe()) updateBasicInterviewDto: UpdateBasicInterviewDto
    ) {
        return this.basicInterviewService.updateBasicInterview(id, updateBasicInterviewDto);
    }

    @Delete(':id')
    deleteBasicInterview(
        @Param('id') id: number
    ) {
        return this.basicInterviewService.deleteBasicInterview(id);
    }
}
