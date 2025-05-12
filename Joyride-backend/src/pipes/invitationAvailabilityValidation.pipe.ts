import { ArgumentMetadata, BadRequestException, PipeTransform } from "@nestjs/common";
import { InvitationStatus } from "src/Entity/invitation.entity";


export class InvitationStatusValidationPipe implements PipeTransform{
    readonly allowedStatus: InvitationStatus[]= [InvitationStatus.upComing, InvitationStatus.celebrated];

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