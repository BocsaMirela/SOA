import {IsString, IsInt} from 'class-validator';
import {ApiProperty} from "@nestjs/swagger";

export class Item {
    @ApiProperty()
    @IsString() id: string;

    @ApiProperty()
    @IsString() title: string;

    @ApiProperty()
    @IsString() description: string;

    @ApiProperty()
    @IsString() brand: string;

    @ApiProperty()
    @IsInt() price: number;
}
