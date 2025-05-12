import { AddSpecialInterviewDto } from 'src/DTO/add-specialInterview.dto';
import { UpdateSpecialInterviewDto } from 'src/DTO/update-specialInterview.dto';
import { SpecialInterviewEntity } from 'src/Entity/specialInterview.entity';
import { Repository } from 'typeorm';
export declare class SpecialInterviewService {
    private repo;
    constructor(repo: Repository<SpecialInterviewEntity>);
    getAllSpecialInterviews(): Promise<SpecialInterviewEntity[]>;
    getSpecialInterviewById(id: number): Promise<SpecialInterviewEntity>;
    addSpecialInterview(userId: number, addSpecialInterviewDTO: AddSpecialInterviewDto): Promise<SpecialInterviewEntity>;
    updateSpecialInterview(id: number, updateSpecialInterviewDto: UpdateSpecialInterviewDto): Promise<SpecialInterviewEntity>;
    deleteSpecialInterview(id: number): Promise<import("typeorm").DeleteResult>;
}
