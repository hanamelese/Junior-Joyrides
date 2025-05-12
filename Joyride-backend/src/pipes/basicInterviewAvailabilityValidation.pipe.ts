import { ArgumentMetadata, BadRequestException, PipeTransform } from "@nestjs/common";
import { BasicInterviewStatus } from "src/Entity/basicInterview.entity";


export class BasicInterviewStatusValidationPipe implements PipeTransform{
    readonly allowedStatus: BasicInterviewStatus[]= [BasicInterviewStatus.upComing, BasicInterviewStatus.hosted];

    transform(value: any, metadata: ArgumentMetadata) {
        value= value?.toUpperCase();

        if (!this.isStatusValid(value)){
            throw new BadRequestException(`${value} is an invalid availability status`);
        }
        return value; 
    }
    
    private isStatusValid(status:any) :boolean{
        const index:number = this.allowedStatus.indexOf(status);
        return index !== -1;
    }
}

//used this pipe for both the basicInterview and specialInterview entity since they have common status enum of upcoming and hosted.