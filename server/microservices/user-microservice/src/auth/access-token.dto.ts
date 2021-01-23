import {IsString} from 'class-validator';
import {ApiProperty} from '@nestjs/swagger';

export class AccessToken {
    @ApiProperty()
    @IsString() access_token: string;
}
