import { WishListService } from './wishlist.service';
import { AddWishListDto } from 'src/DTO/add-wishlist.dto';
import { UpdateWishListDto } from 'src/DTO/update-wishlist.dto';
import { UserEntity } from 'src/Entity/user.entity';
export declare class WishListController {
    private wishListService;
    constructor(wishListService: WishListService);
    getAllWishes(): Promise<import("../Entity/wishlist.entity").WishListEntity[]>;
    getWishById(id: number): Promise<import("../Entity/wishlist.entity").WishListEntity>;
    addWish(user: UserEntity, data: AddWishListDto): Promise<import("../Entity/wishlist.entity").WishListEntity>;
    updateWish(id: number, updateWishListDto: UpdateWishListDto): Promise<import("../Entity/wishlist.entity").WishListEntity>;
    deleteWish(id: number): Promise<import("typeorm").DeleteResult>;
}
