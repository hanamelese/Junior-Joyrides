import { UserEntity } from 'src/Entity/user.entity';
import { SpecialInterviewService } from './specialInterview.service';
import { AddSpecialInterviewDto } from 'src/DTO/add-specialInterview.dto';
import { UpdateSpecialInterviewDto } from 'src/DTO/update-specialInterview.dto';
export declare class SpecialInterviewController {
    private specialInterviewService;
    constructor(specialInterviewService: SpecialInterviewService);
    getAllSpecialInterviews(): Promise<import("../Entity/specialInterview.entity").SpecialInterviewEntity[]>;
    getSpecialInterviewById(id: number): Promise<import("../Entity/specialInterview.entity").SpecialInterviewEntity>;
    addSpecialInterview(user: UserEntity, data: AddSpecialInterviewDto): Promise<import("../Entity/specialInterview.entity").SpecialInterviewEntity>;
    updateSpecialInterview(id: number, updateSpecialInterviewDto: UpdateSpecialInterviewDto): Promise<import("../Entity/specialInterview.entity").SpecialInterviewEntity>;
    deleteSpecialInterview(id: number): Promise<import("typeorm").DeleteResult>;
}
