import { IsNotEmpty, Matches, MaxLength, MinLength } from "class-validator";

export class RegisterUserDTO {
    @IsNotEmpty()
    firstName: string;
    @IsNotEmpty()
    lastName: string;
    @IsNotEmpty()
    email: string;
    @IsNotEmpty()
    @MinLength(6) @MaxLength(12)
    @Matches(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,12}$/, { 
        message: "Password too weak, choose a stronger password between 6 and 12 characters." 
    })
    password: string;
   
}