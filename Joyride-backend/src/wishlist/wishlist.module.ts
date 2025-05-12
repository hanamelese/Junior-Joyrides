import { Module } from '@nestjs/common';
import { WishListController } from './wishlist.controller';
import { WishListService } from './wishlist.service';
import { TypeOrmModule } from '@nestjs/typeorm';
import { WishListEntity } from 'src/Entity/wishlist.entity';

@Module({
  imports: [
    TypeOrmModule.forFeature([WishListEntity])
  ],
  controllers: [WishListController],
  providers: [WishListService]
})
export class WishListModule {}
