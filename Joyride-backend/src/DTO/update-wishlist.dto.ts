import { IsOptional, IsString, IsNumber } from 'class-validator';
import { WishListStatus } from 'src/Entity/wishlist.entity';

export class UpdateWishListDto {
  @IsOptional()
  @IsString()
  childName?: string;

  @IsOptional() 
  @IsString() 
  guardianEmail: string;

  @IsOptional()
  @IsNumber()
  age?: number;

  @IsOptional()
  @IsString()
  specialRequests?: string;

  @IsOptional()
  @IsString()
  date?: string;

  @IsOptional() 
  @IsString() 
  imageUrl?: string;

  @IsOptional()
  upcoming?: boolean;

  @IsOptional()
  appreoved?: boolean;
}