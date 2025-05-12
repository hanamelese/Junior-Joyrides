import { UserEntity } from 'src/Entity/user.entity';
import { BasicInterviewService } from './basicInterview.service';
import { AddBasicInterviewDto } from 'src/DTO/add-basicInterview.dto';
import { UpdateBasicInterviewDto } from 'src/DTO/update-basicInterview.dto';
export declare class BasicInterviewController {
    private basicInterviewService;
    constructor(basicInterviewService: BasicInterviewService);
    getAllBasicInterviews(): Promise<import("../Entity/basicInterview.entity").BasicInterviewEntity[]>;
    getBasicInterviewById(id: number): Promise<import("../Entity/basicInterview.entity").BasicInterviewEntity>;
    addBasicInterview(user: UserEntity, data: AddBasicInterviewDto): Promise<import("../Entity/basicInterview.entity").BasicInterviewEntity>;
    updateBasicInterview(id: number, updateBasicInterviewDto: UpdateBasicInterviewDto): Promise<import("../Entity/basicInterview.entity").BasicInterviewEntity>;
    deleteBasicInterview(id: number): Promise<import("typeorm").DeleteResult>;
}
