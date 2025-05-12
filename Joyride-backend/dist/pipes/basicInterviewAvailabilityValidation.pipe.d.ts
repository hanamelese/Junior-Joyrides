import { ArgumentMetadata, PipeTransform } from "@nestjs/common";
import { BasicInterviewStatus } from "src/Entity/basicInterview.entity";
export declare class BasicInterviewStatusValidationPipe implements PipeTransform {
    readonly allowedStatus: BasicInterviewStatus[];
    transform(value: any, metadata: ArgumentMetadata): any;
    private isStatusValid;
}
