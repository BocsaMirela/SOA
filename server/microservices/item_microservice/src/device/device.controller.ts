import {Body, Controller, HttpStatus, Post, Res, UseGuards,} from '@nestjs/common';
import {DeviceService} from './device.service';
import {JwtAuthGuard} from "../auth/jwt-auth.guard";
import {CreateToken} from "./create-token.dto";
import {ApiBody, ApiTags} from '@nestjs/swagger';

@UseGuards(JwtAuthGuard)
@Controller('api/device')
@ApiTags('device')
export class DeviceController {
    constructor(
        private readonly deviceService: DeviceService,
    ) {
    }

    @Post('updateFcmToken')
    @ApiBody({type: CreateToken})
    async create(@Res() res, @Body() fcmToken: CreateToken): Promise<any> {
        try {
            const item = await this.deviceService.add(fcmToken);
            return res.send({status: "success"});
        } catch (error) {
            return res.status(HttpStatus.BAD_REQUEST).send(error);
        }
    }

}
