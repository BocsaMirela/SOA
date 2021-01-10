import {InMemoryDBEntity} from '@nestjs-addons/in-memory-db';
import {IsString, IsInt} from 'class-validator';

export class Item implements InMemoryDBEntity {
    @IsString() id: string;
    @IsString() title: string;
    @IsString() description: string;
    @IsString() brand: string;
    @IsInt() price: number;
    @IsInt() version: number;
}
