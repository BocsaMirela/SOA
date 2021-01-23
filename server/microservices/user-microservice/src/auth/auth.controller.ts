import {Body, Controller, Get, Post, Request, UseGuards} from '@nestjs/common';
import {AuthService} from './auth.service';
import {LocalAuthGuard} from './local-auth.guard';
import {JwtAuthGuard} from "./jwt-auth.guard";
import {ApiCreatedResponse} from "@nestjs/swagger";
import {User} from "../user/interfaces/user.interface";
import {CreateUser} from "../user/create-user.dto";
import {AccessToken} from "./access-token.dto";

@Controller()
export class AuthController {
    constructor(private readonly authService: AuthService) {
    }

    @UseGuards(LocalAuthGuard)
    @Post('api/auth/login')
    @ApiCreatedResponse({
        description: 'User successfully logged in',
        type: AccessToken,
    })
    async login(@Request() req, @Body() userDto: CreateUser) {
        return this.authService.login(req.user);
    }

    @UseGuards(JwtAuthGuard)
    @Get('/api/profile')
    @ApiCreatedResponse({
        description: 'User information',
        type: User,
    })
    public user(@Request() req) {
        console.log('user ' + req.user)
        return {
            id: req.user.id,
            username: req.user.username
        };
    }
}
