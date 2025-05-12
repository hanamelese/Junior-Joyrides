import { Injectable, InternalServerErrorException, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { AddWishListDto } from 'src/DTO/add-wishlist.dto';
import { UpdateWishListDto } from 'src/DTO/update-wishlist.dto';
import { WishListEntity, WishListStatus } from 'src/Entity/wishlist.entity';
import { Repository } from 'typeorm';

@Injectable()
export class WishListService {
    constructor(@InjectRepository(WishListEntity) private repo: Repository<WishListEntity>) {}

    async getAllWishes() {
        return await this.repo.find();
    }

    async getWishById(id: number) {
        const wish = await this.repo.findOne({ where: { id } });
        if (!wish) throw new NotFoundException(`Wish with ID ${id} not found`);
        return wish;
    }

    async addWish(userId: number, addWishDTO: AddWishListDto) {
        const wish: WishListEntity = new WishListEntity();
        const { childName, age, guardianEmail, date, specialRequests, imageUrl } = addWishDTO;

        wish.childName = childName;
        wish.age = age;
        wish.guardianEmail = guardianEmail;
        wish.upcoming = true;
        wish.approved = false;
        wish.date = date;
        wish.specialRequests = specialRequests;
        wish.imageUrl = imageUrl;
        wish.userId = userId;

        this.repo.create(wish);
        try {
            return await this.repo.save(wish);
        } catch (err) {
            throw new InternalServerErrorException(`Something went wrong, wish not created. ${err.message}`);
        }
    }

    async updateWish(id: number, updateWishDto: UpdateWishListDto) {
        await this.repo.update({ id }, updateWishDto);
        return this.repo.findOne({ where: { id } });
    }

    async deleteWish(id: number) {
        try {
            const result = await this.repo.delete({ id });
            if (result.affected === 0) {
                throw new NotFoundException(`Wish with ID ${id} not found`);
            }
            return result;
        } catch (err) {
            throw new InternalServerErrorException('Something went wrong');
        }
    }
}
