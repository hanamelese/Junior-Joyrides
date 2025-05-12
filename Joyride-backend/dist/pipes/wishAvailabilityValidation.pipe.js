"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.WishListStatusValidationPipe = void 0;
const common_1 = require("@nestjs/common");
const wishlist_entity_1 = require("../Entity/wishlist.entity");
class WishListStatusValidationPipe {
    constructor() {
        this.allowedStatus = [wishlist_entity_1.WishListStatus.upComing, wishlist_entity_1.WishListStatus.posted];
    }
    transform(value, metadata) {
        value = value?.toUpperCase();
        if (!this.isStatusValid(value)) {
            throw new common_1.BadRequestException(`${value} is an invalid availability status`);
        }
        return value;
    }
    isStatusValid(status) {
        const index = this.allowedStatus.indexOf(status);
        return index !== -1;
    }
}
exports.WishListStatusValidationPipe = WishListStatusValidationPipe;
//# sourceMappingURL=wishAvailabilityValidation.pipe.js.map