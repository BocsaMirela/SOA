import {IsString} from 'class-validator';

export class CreateToken {
  @IsString() token: string;
}
