import { Body, Controller, Delete, Get, Param, Patch, Post, ValidationPipe, UseGuards } from '@nestjs/common';
import { WishListService } from './wishlist.service';
import { AddWishListDto } from 'src/DTO/add-wishlist.dto';
import { UpdateWishListDto } from 'src/DTO/update-wishlist.dto';
import { AuthGuard } from '@nestjs/passport';
import { UserEntity } from 'src/Entity/user.entity';
import { User } from 'src/auth/user.decorator';

@Controller('api/wishLists')
export class WishListController {
    constructor(private wishListService: WishListService) {}

    @Get()
    getAllWishes() {
        return this.wishListService.getAllWishes();
    }

    @Get(':id')
    getWishById(
        @Param('id') id: number
    ) {
        return this.wishListService.getWishById(id);
    }

    @Post()
    @UseGuards(AuthGuard('jwt'))
    addWish(@User() user: UserEntity, @Body(ValidationPipe) data: AddWishListDto) {
        return this.wishListService.addWish(user.id, data);
    }

    @Patch(':id')
    updateWish(
        @Param('id') id: number,
        @Body(new ValidationPipe()) updateWishListDto: UpdateWishListDto
    ) {
        return this.wishListService.updateWish(id, updateWishListDto);
    }

    @Delete(':id')
    deleteWish(
        @Param('id') id: number
    ) {
        return this.wishListService.deleteWish(id);
    }
}
