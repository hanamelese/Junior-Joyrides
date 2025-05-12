import { Injectable, InternalServerErrorException, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { AddSpecialInterviewDto } from 'src/DTO/add-specialInterview.dto';
import { UpdateSpecialInterviewDto } from 'src/DTO/update-specialInterview.dto';
import { SpecialInterviewEntity } from 'src/Entity/specialInterview.entity';
import { Repository } from 'typeorm';

@Injectable()
export class SpecialInterviewService {
    constructor(@InjectRepository(SpecialInterviewEntity) private repo: Repository<SpecialInterviewEntity>) {}

    async getAllSpecialInterviews() {
        return await this.repo.find();
    }

    async getSpecialInterviewById(id: number) {
        const specialInterview = await this.repo.findOne({ where: { id } });
        if (!specialInterview) throw new NotFoundException(`Special Interview with ID ${id} not found`);
        return specialInterview;
    }

    async addSpecialInterview(userId: number, addSpecialInterviewDTO: AddSpecialInterviewDto) {
        const specialInterview: SpecialInterviewEntity = new SpecialInterviewEntity();
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
        } catch (err) {
            throw new InternalServerErrorException(`Something went wrong, item not created. ${err.message}`);
        }
    }

    async updateSpecialInterview(id: number, updateSpecialInterviewDto: UpdateSpecialInterviewDto) {
        await this.repo.update({ id }, updateSpecialInterviewDto);
        return this.repo.findOne({ where: { id } });
    }

    async deleteSpecialInterview(id: number) {
        try {
            return await this.repo.delete({ id });
        } catch (err) {
            throw new InternalServerErrorException('Something went wrong');
        }
    }
}