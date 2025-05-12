"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var __param = (this && this.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.WishListService = void 0;
const common_1 = require("@nestjs/common");
const typeorm_1 = require("@nestjs/typeorm");
const wishlist_entity_1 = require("../Entity/wishlist.entity");
const typeorm_2 = require("typeorm");
let WishListService = class WishListService {
    constructor(repo) {
        this.repo = repo;
    }
    async getAllWishes() {
        return await this.repo.find();
    }
    async getWishById(id) {
        const wish = await this.repo.findOne({ where: { id } });
        if (!wish)
            throw new common_1.NotFoundException(`Wish with ID ${id} not found`);
        return wish;
    }
    async addWish(userId, addWishDTO) {
        const wish = new wishlist_entity_1.WishListEntity();
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
        }
        catch (err) {
            throw new common_1.InternalServerErrorException(`Something went wrong, wish not created. ${err.message}`);
        }
    }
    async updateWish(id, updateWishDto) {
        await this.repo.update({ id }, updateWishDto);
        return this.repo.findOne({ where: { id } });
    }
    async deleteWish(id) {
        try {
            const result = await this.repo.delete({ id });
            if (result.affected === 0) {
                throw new common_1.NotFoundException(`Wish with ID ${id} not found`);
            }
            return result;
        }
        catch (err) {
            throw new common_1.InternalServerErrorException('Something went wrong');
        }
    }
};
exports.WishListService = WishListService;
exports.WishListService = WishListService = __decorate([
    (0, common_1.Injectable)(),
    __param(0, (0, typeorm_1.InjectRepository)(wishlist_entity_1.WishListEntity)),
    __metadata("design:paramtypes", [typeorm_2.Repository])
], WishListService);
//# sourceMappingURL=wishlist.service.js.map