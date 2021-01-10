import {Injectable} from '@nestjs/common';
import {UserService} from '../user/user.service';
import {JwtService} from '@nestjs/jwt';
import {IUser} from "../user/user";

@Injectable()
export class AuthService {
    constructor(
        private readonly usersService: UserService,
        private readonly jwtService: JwtService,
    ) {
    }

    async validateUser(username: string, pass: string): Promise<any> {
        const user = await this.usersService.findOne(username);
        console.log('validateUser', username, pass)
        if (user && user.password === pass) {
            return user;
        }
        return null;
    }

    async findByPayload({username}: any): Promise<IUser> {
        return await this.usersService.findOne(username);
    }

    async login(user: any) {
        const payload = {username: user.username, sub: user.userId};
        console.log('payload login ' + payload)
        return {
            access_token: this.jwtService.sign(payload),
        }
    }
}
