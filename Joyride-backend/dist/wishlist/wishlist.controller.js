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
exports.WishListController = void 0;
const common_1 = require("@nestjs/common");
const wishlist_service_1 = require("./wishlist.service");
const add_wishlist_dto_1 = require("../DTO/add-wishlist.dto");
const update_wishlist_dto_1 = require("../DTO/update-wishlist.dto");
const passport_1 = require("@nestjs/passport");
const user_entity_1 = require("../Entity/user.entity");
const user_decorator_1 = require("../auth/user.decorator");
let WishListController = class WishListController {
    constructor(wishListService) {
        this.wishListService = wishListService;
    }
    getAllWishes() {
        return this.wishListService.getAllWishes();
    }
    getWishById(id) {
        return this.wishListService.getWishById(id);
    }
    addWish(user, data) {
        return this.wishListService.addWish(user.id, data);
    }
    updateWish(id, updateWishListDto) {
        return this.wishListService.updateWish(id, updateWishListDto);
    }
    deleteWish(id) {
        return this.wishListService.deleteWish(id);
    }
};
exports.WishListController = WishListController;
__decorate([
    (0, common_1.Get)(),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", []),
    __metadata("design:returntype", void 0)
], WishListController.prototype, "getAllWishes", null);
__decorate([
    (0, common_1.Get)(':id'),
    __param(0, (0, common_1.Param)('id')),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Number]),
    __metadata("design:returntype", void 0)
], WishListController.prototype, "getWishById", null);
__decorate([
    (0, common_1.Post)(),
    (0, common_1.UseGuards)((0, passport_1.AuthGuard)('jwt')),
    __param(0, (0, user_decorator_1.User)()),
    __param(1, (0, common_1.Body)(common_1.ValidationPipe)),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [user_entity_1.UserEntity, add_wishlist_dto_1.AddWishListDto]),
    __metadata("design:returntype", void 0)
], WishListController.prototype, "addWish", null);
__decorate([
    (0, common_1.Patch)(':id'),
    __param(0, (0, common_1.Param)('id')),
    __param(1, (0, common_1.Body)(new common_1.ValidationPipe())),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Number, update_wishlist_dto_1.UpdateWishListDto]),
    __metadata("design:returntype", void 0)
], WishListController.prototype, "updateWish", null);
__decorate([
    (0, common_1.Delete)(':id'),
    __param(0, (0, common_1.Param)('id')),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Number]),
    __metadata("design:returntype", void 0)
], WishListController.prototype, "deleteWish", null);
exports.WishListController = WishListController = __decorate([
    (0, common_1.Controller)('api/wishLists'),
    __metadata("design:paramtypes", [wishlist_service_1.WishListService])
], WishListController);
//# sourceMappingURL=wishlist.controller.js.map