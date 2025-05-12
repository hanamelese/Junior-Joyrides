import { AddBasicInterviewDto } from 'src/DTO/add-basicInterview.dto';
import { UpdateBasicInterviewDto } from 'src/DTO/update-basicInterview.dto';
import { BasicInterviewEntity } from 'src/Entity/basicInterview.entity';
import { Repository } from 'typeorm';
export declare class BasicInterviewService {
    private repo;
    constructor(repo: Repository<BasicInterviewEntity>);
    getAllBasicInterviews(): Promise<BasicInterviewEntity[]>;
    getBasicInterviewById(id: number): Promise<BasicInterviewEntity>;
    addBasicInterview(userId: number, addBasicInterviewDTO: AddBasicInterviewDto): Promise<BasicInterviewEntity>;
    updateBasicInterview(id: number, updateBasicInterviewDto: UpdateBasicInterviewDto): Promise<BasicInterviewEntity>;
    deleteBasicInterview(id: number): Promise<import("typeorm").DeleteResult>;
}
