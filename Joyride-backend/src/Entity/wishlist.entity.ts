import { Column, Entity, PrimaryGeneratedColumn, ManyToOne, JoinColumn } from "typeorm";
import { UserEntity } from "./user.entity";

@Entity()
export class WishListEntity {
    @PrimaryGeneratedColumn()
    id: number;

    @Column()
    childName: string;

    @Column()
    guardianEmail: string;

    @Column()
    age: number;

    @Column()
    date: string;

    @Column()
    specialRequests: string;

    @Column()
    imageUrl: string;

    @Column()
    upcoming: boolean;

    @Column()
    approved: boolean;

    @Column()
    userId: number;

    @ManyToOne(() => UserEntity, user => user.wishLists)
    @JoinColumn({ name: 'userId' })
    user: UserEntity;
}

export enum WishListStatus {
    upComing = 'UPCOMING',
    posted = 'POSTED'
}
