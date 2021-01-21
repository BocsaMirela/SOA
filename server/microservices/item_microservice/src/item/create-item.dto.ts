import {IsInt, IsString} from 'class-validator';

export class CreateItem {
  @IsString() title: string;
  @IsString() description: string;
  @IsString() brand: string;
  @IsInt() price: number;
}
