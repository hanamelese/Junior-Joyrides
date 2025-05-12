import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { BasicInterviewController } from './basicInterview.controller';
import { BasicInterviewService } from './basicInterview.service';
import { BasicInterviewEntity } from 'src/Entity/basicInterview.entity';

@Module({
  imports: [
    TypeOrmModule.forFeature([BasicInterviewEntity])
  ],
  controllers: [BasicInterviewController],
  providers: [BasicInterviewService]
})
export class BasicInterviewModule {}
