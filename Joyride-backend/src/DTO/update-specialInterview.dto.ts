import { IsOptional, IsString, IsNumber } from 'class-validator';
import { SpecialInterviewStatus } from 'src/Entity/specialInterview.entity';

export class UpdateSpecialInterviewDto {
  @IsOptional()
  @IsString()
  childName?: string;

  @IsOptional()
  @IsString()
  guardianName?: string;

  @IsOptional()
  @IsNumber()
  age?: number;

  @IsOptional()
  @IsNumber()
  guardianPhone: number;

  @IsOptional()
  @IsString()
  specialRequests?: string;

  @IsOptional() 
  @IsString() 
  imageUrl?: string;

  @IsOptional() 
  @IsString() 
  guardianEmail: string;

  @IsOptional()
  upcoming?: boolean;   

  @IsOptional()
  appreoved?: boolean;
}