import { Injectable, InternalServerErrorException, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { AddBasicInterviewDto } from 'src/DTO/add-basicInterview.dto';
import { UpdateBasicInterviewDto } from 'src/DTO/update-basicInterview.dto';
import { BasicInterviewEntity } from 'src/Entity/basicInterview.entity';
import { Repository } from 'typeorm';

@Injectable()
export class BasicInterviewService {
    constructor(@InjectRepository(BasicInterviewEntity) private repo: Repository<BasicInterviewEntity>) {}

    async getAllBasicInterviews() {
        return await this.repo.find();
    }

    async getBasicInterviewById(id: number) {
        const basicInterview = await this.repo.findOne({ where: { id } });
        if (!basicInterview) throw new NotFoundException(`Basic Interview with ID ${id} not found`);
        return basicInterview;
    }

    async addBasicInterview(userId: number, addBasicInterviewDTO: AddBasicInterviewDto) {
        const basicInterview: BasicInterviewEntity = new BasicInterviewEntity();
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
        } catch (err) {
            throw new InternalServerErrorException(`Something went wrong, item not created. ${err.message}`);
        }
    }

    async updateBasicInterview(id: number, updateBasicInterviewDto: UpdateBasicInterviewDto) {
        await this.repo.update({ id }, updateBasicInterviewDto);
        return this.repo.findOne({ where: { id } });
    }

    async deleteBasicInterview(id: number) {
        try {
            return await this.repo.delete({ id });
        } catch (err) {
            throw new InternalServerErrorException('Something went wrong');
        }
    }
}
