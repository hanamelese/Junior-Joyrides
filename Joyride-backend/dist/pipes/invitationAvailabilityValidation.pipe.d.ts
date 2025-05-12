import { ArgumentMetadata, PipeTransform } from "@nestjs/common";
import { InvitationStatus } from "src/Entity/invitation.entity";
export declare class InvitationStatusValidationPipe implements PipeTransform {
    readonly allowedStatus: InvitationStatus[];
    transform(value: any, metadata: ArgumentMetadata): any;
    private isStatusValid;
}
