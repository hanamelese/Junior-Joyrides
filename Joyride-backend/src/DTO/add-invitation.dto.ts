import { IsDate, IsNotEmpty, MaxLength } from "class-validator";

export class AddInvitationDto{
    @IsNotEmpty()
    @MaxLength(15, {message: 'Max length is 15 characters.'})
    childName: string;
    @IsNotEmpty()
    age: number;
    @IsNotEmpty()
    address: string;
    @IsNotEmpty()
    guardianPhone: number;
    @IsNotEmpty()
    guardianEmail: string;
    @IsNotEmpty()
    specialRequests: string;
    @IsNotEmpty()
    date: string;
    @IsNotEmpty()
    time: number;
}
