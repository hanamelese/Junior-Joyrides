import { ArgumentMetadata, PipeTransform } from "@nestjs/common";
import { WishListStatus } from "src/Entity/wishlist.entity";
export declare class WishListStatusValidationPipe implements PipeTransform {
    readonly allowedStatus: WishListStatus[];
    transform(value: any, metadata: ArgumentMetadata): any;
    private isStatusValid;
}
