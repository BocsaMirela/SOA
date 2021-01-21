import {IsString, IsInt} from 'class-validator';

export class Item {
    @IsString() id: string;
    @IsString() title: string;
    @IsString() description: string;
    @IsString() brand: string;
    @IsInt() price: number;
}
