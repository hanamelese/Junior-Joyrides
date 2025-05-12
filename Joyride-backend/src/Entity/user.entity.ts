import { Column, Entity, PrimaryGeneratedColumn, OneToMany } from "typeorm";
import { InvitationEntity } from "./invitation.entity";
import { WishListEntity } from "./wishlist.entity";
import { BasicInterviewEntity } from "./basicInterview.entity";
import { SpecialInterviewEntity } from "./specialInterview.entity";

@Entity()
export class UserEntity {
    @PrimaryGeneratedColumn()
    id: number;

    @Column()
    firstName: string;

    @Column()
    lastName: string;

    @Column()
    email: string;

    @Column()
    password: string;

    @Column()
    salt: string;

    @Column()
    profileImageUrl: string;

    @Column()
    backgroundImageUrl: string;

    @Column({ default: "user" })
    role: string;

    @OneToMany(() => InvitationEntity, invitation => invitation.user)
    invitations: InvitationEntity[];

    @OneToMany(() => BasicInterviewEntity, basicInterview => basicInterview.user)
    basicInterviews: BasicInterviewEntity[];

    @OneToMany(() => SpecialInterviewEntity, specialInterview => specialInterview.user)
    specialInterviews: SpecialInterviewEntity[];

    @OneToMany(() => WishListEntity, wishList => wishList.user)
    wishLists: WishListEntity[];
}