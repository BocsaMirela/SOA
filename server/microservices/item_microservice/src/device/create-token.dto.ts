import {IsString} from 'class-validator';
import {ApiProperty} from '@nestjs/swagger';

export class CreateToken {
    @ApiProperty()
    @IsString() token: string;
}
