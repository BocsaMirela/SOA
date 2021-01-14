import {Controller, Get, Post, Request, UseGuards} from '@nestjs/common';
import {AuthService} from './auth.service';
import {LocalAuthGuard} from './local-auth.guard';
import {JwtAuthGuard} from "./jwt-auth.guard";

@Controller()
export class AuthController {
    constructor(private readonly authService: AuthService) {
    }

    @UseGuards(LocalAuthGuard)
    @Post('api/auth/login')
    async login(@Request() req) {
        return this.authService.login(req.user);
    }

    @Get('/api/profile')
    @UseGuards(JwtAuthGuard)
    public user(@Request() req) {
        console.log('user ' + req.user)
        return {
            id: req.user._id,
            userId: req.user.username
        };
    }
}
