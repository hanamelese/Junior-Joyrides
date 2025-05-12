import { Module, Options } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { WishListModule } from './wishlist/wishlist.module';
import { TypeOrmModule, TypeOrmModuleOptions } from '@nestjs/typeorm';
import { AuthModule } from './auth/auth.module';
import { ServeStaticModule } from '@nestjs/serve-static';
import { join } from 'path';
import { InvitationModule } from './invitation/invitation.module';
//loads the environment variables
import * as dotenv from 'dotenv';
import { BasicInterviewModule } from './basicInterview/basicInterview.module';
import { SpecialInterviewModule } from './specialInterview/specialInterview.module';
dotenv.config();


const ormOptions: TypeOrmModuleOptions={
  type: "mysql",
  host: process.env.DB_HOST,
  port: parseInt(process.env.DB_PORT, 10),
  username: process.env.DB_USERNAME,
  password: process.env.DB_PASSWORD,
  database: process.env.DB_DATABASE,
  autoLoadEntities: true,
  synchronize: false,
  migrationsRun: true,
};

@Module({
  imports: [
    TypeOrmModule.forRoot(ormOptions),
    // ServeStaticModule.forRoot({ rootPath: join(__dirname, '..', '..', 'charity-frontend','charity_system_frontend'),}),
    AuthModule,
    InvitationModule,
    BasicInterviewModule,
    SpecialInterviewModule,
    WishListModule ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
