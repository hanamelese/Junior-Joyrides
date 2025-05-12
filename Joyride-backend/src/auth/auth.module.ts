import { Module } from '@nestjs/common';
import { AuthController } from './auth.controller';
import { AuthService } from './auth.service';
import { TypeOrmModule } from '@nestjs/typeorm';
import { UserEntity } from 'src/Entity/user.entity';
import { JwtModule } from '@nestjs/jwt';
import { PassportModule } from '@nestjs/passport';
import { JwtCustomStrategy } from './jwt-custom.strategy';
import { UserController } from './user.controller';

@Module({
  imports:[
    TypeOrmModule.forFeature([UserEntity]),
    JwtModule.register({
      secret: 'uyghlkowielewofjeiu7r74huhu8',
      signOptions: {
        algorithm: 'HS512',
        expiresIn: '1d',
      }
    }),
    PassportModule.register({
      dafaultStrategy:'jwt'
    })
  ],
  controllers: [AuthController, UserController],
  providers: [AuthService, JwtCustomStrategy],
})
export class AuthModule {}
