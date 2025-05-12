import { ArgumentMetadata, BadRequestException, PipeTransform } from "@nestjs/common";
import { WishListStatus } from "src/Entity/wishlist.entity";

export class WishListStatusValidationPipe implements PipeTransform{
    readonly allowedStatus: WishListStatus[]= [WishListStatus.upComing, WishListStatus.posted];

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