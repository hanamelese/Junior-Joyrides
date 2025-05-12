import { IsNotEmpty, IsOptional, MaxLength } from "class-validator";

export class AddWishListDto{
    @IsNotEmpty()
    @MaxLength(15, {message: 'Max length is 15 characters.'})
    childName: string;
    @IsNotEmpty()
    guardianEmail: string;
    @IsNotEmpty()
    age: number;
    @IsNotEmpty()
    specialRequests: string;
    @IsNotEmpty()
    date: string;
    @IsNotEmpty() 
    imageUrl: string;
}
