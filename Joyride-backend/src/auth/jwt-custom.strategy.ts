import { UnauthorizedException } from "@nestjs/common";
import { PassportStrategy } from "@nestjs/passport";
import { InjectRepository } from "@nestjs/typeorm";
import { ExtractJwt, Strategy } from "passport-jwt";
import { UserEntity } from "src/Entity/user.entity";
import { Repository } from "typeorm";

export class JwtCustomStrategy extends PassportStrategy(Strategy){
    constructor(@InjectRepository(UserEntity) private repo: Repository<UserEntity>){
        super({
            jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(),
            secretOrKey: 'uyghlkowielewofjeiu7r74huhu8'
        });
    }

    async validate(payLoad: {email:string}){
        const {email}=payLoad;
        const user: UserEntity=await this.repo.findOne({where: {email}});

        if (!user) { 
            throw new UnauthorizedException(); 
        } 
        return user;
    }
}