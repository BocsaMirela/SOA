import { IsString } from 'class-validator';

export class CreateUser {
  @IsString() username: string;
  @IsString() password: string;
}
