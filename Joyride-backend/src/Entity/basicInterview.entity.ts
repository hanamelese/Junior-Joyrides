import { Column, Entity, PrimaryGeneratedColumn, ManyToOne, JoinColumn } from "typeorm";
import { UserEntity } from "./user.entity";

@Entity()
export class BasicInterviewEntity {
    @PrimaryGeneratedColumn()
    id: number;

    @Column()
    childName: string;

    @Column()
    guardianName: string;

    @Column()
    guardianPhone: number;

    @Column()
    age: number;

    @Column()
    guardianEmail: string;

    @Column()
    specialRequests: string;

    @Column()
    upcoming: boolean;

    @Column()
    approved: boolean;

    @Column()
    userId: number;

    @ManyToOne(() => UserEntity, user => user.basicInterviews)
    @JoinColumn({ name: 'userId' })
    user: UserEntity;
}

export enum BasicInterviewStatus {
    upComing = 'UPCOMING',
    hosted = 'HOSTED'
}