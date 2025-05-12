import { IsOptional, IsString, IsNumber } from 'class-validator';
import { InvitationStatus } from 'src/Entity/invitation.entity';

export class UpdateInvitationDto {
  @IsOptional()
  @IsString()
  childName?: string;

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
  address: string;

  @IsOptional()
  @IsNumber()
  time: number;

  @IsOptional()
  @IsString()
  date: string;

  @IsOptional() 
  @IsString() 
  guardianEmail: string;

  @IsOptional()
  upcoming?: boolean;

  @IsOptional()
  appreoved?: boolean;
}



