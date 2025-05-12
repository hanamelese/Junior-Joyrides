import { IsOptional, IsString, IsNumber } from 'class-validator';
import { BasicInterviewStatus } from 'src/Entity/basicInterview.entity';

export class UpdateBasicInterviewDto {
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
  guardianEmail: string;

  @IsOptional()
  upcoming?: boolean;

  @IsOptional()
  appreoved?: boolean;
}