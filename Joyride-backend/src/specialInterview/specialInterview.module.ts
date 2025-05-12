import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { SpecialInterviewController } from './specialInterview.controller';
import { SpecialInterviewService } from './specialInterview.service';
import { SpecialInterviewEntity } from 'src/Entity/specialInterview.entity';

@Module({
  imports: [
    TypeOrmModule.forFeature([SpecialInterviewEntity])
  ],
  controllers: [SpecialInterviewController],
  providers: [SpecialInterviewService]
})
export class SpecialInterviewModule {}
