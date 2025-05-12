import { IsDate, IsNotEmpty, MaxLength } from "class-validator";

export class AddBasicInterviewDto{
    @IsNotEmpty()
    @MaxLength(15, {message: 'Max length is 15 characters.'})
    childName: string;
    @IsNotEmpty()
    @MaxLength(15, {message: 'Max length is 15 characters.'})
    guardianName: string;
    @IsNotEmpty()
    age: number;
    @IsNotEmpty()
    guardianPhone: number;
    @IsNotEmpty()
    guardianEmail: string;
    @IsNotEmpty()
    specialRequests: string;
}