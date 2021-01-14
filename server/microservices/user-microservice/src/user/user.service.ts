import {Injectable} from '@nestjs/common';
import {IUser} from './user';
import {Model} from "mongoose";
import {InjectModel} from "@nestjs/mongoose";
import {CreateUser} from "./create-user.dto";

@Injectable()
export class UserService {
    constructor(@InjectModel('User') private readonly model: Model<IUser>,
    ) {
    //     this.create({
    //         username: 'mirela.bocsa@yahoo.com',
    //         password: '123456',
    //     })
    //     this.create({
    //         username: 'email@yahoo.com',
    //         password: '123456',
    //     })
    //     this.create({
    //         username: 'a',
    //         password: 'a',
    //     })
    }

    async findOne(username: string): Promise<IUser | undefined> {
        return this.model.findOne({username: userId});
    }

    async create(dto: CreateUser): Promise<IUser> {
        const user = new this.model(dto);
        return await user.save();
    }
}
