import { Column, Entity, PrimaryGeneratedColumn, ManyToOne, JoinColumn } from "typeorm";
import { UserEntity } from "./user.entity";

@Entity()
export class InvitationEntity {
    @PrimaryGeneratedColumn()
    id: number;

    @Column()
    childName: string;

    @Column()
    guardianPhone: number;

    @Column()
    age: number;

    @Column()
    guardianEmail: string;

    @Column()
    specialRequests: string;

    @Column()
    address: string;

    @Column()
    date: string;

    @Column()
    time: number;

    @Column()
    upcoming: boolean;

    @Column()
    approved: boolean;

    @Column()
    userId: number;

    @ManyToOne(() => UserEntity, user => user.invitations)
    @JoinColumn({ name: 'userId' })
    user: UserEntity;
}

export enum InvitationStatus {
    upComing = 'UPCOMING',
    celebrated = 'CELEBRATED'
}