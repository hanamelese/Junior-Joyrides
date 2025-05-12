import { AddWishListDto } from 'src/DTO/add-wishlist.dto';
import { UpdateWishListDto } from 'src/DTO/update-wishlist.dto';
import { WishListEntity } from 'src/Entity/wishlist.entity';
import { Repository } from 'typeorm';
export declare class WishListService {
    private repo;
    constructor(repo: Repository<WishListEntity>);
    getAllWishes(): Promise<WishListEntity[]>;
    getWishById(id: number): Promise<WishListEntity>;
    addWish(userId: number, addWishDTO: AddWishListDto): Promise<WishListEntity>;
    updateWish(id: number, updateWishDto: UpdateWishListDto): Promise<WishListEntity>;
    deleteWish(id: number): Promise<import("typeorm").DeleteResult>;
}
