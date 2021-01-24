import {Injectable} from '@nestjs/common';
import {IUser} from './interfaces/user';
import {Model} from "mongoose";
import {InjectModel} from "@nestjs/mongoose";
import {CreateUser} from "./create-user.dto";
import {HASH_SALT} from "../config";
import {hashSync} from 'bcrypt';


@Injectable()
export class UserService {
    constructor(@InjectModel('User') private readonly model: Model<IUser>,
    ) {
        this.create({
            username: 'mirela.bocsa@yahoo.com',
            password: '123456',
        })
        this.create({
            username: 'email@yahoo.com',
            password: '123456',
        })
        this.create({
            username: 'b',
            password: 'b',
        })
    }

    async findOne(username: string): Promise<IUser | undefined> {
        return this.model.findOne({username});
    }

    async create(dto: CreateUser): Promise<IUser> {
        dto.password = hashSync(dto.password, HASH_SALT)

        const user = new this.model(dto);
        return await user.save();
    }
}
