import { Module } from '@nestjs/common';
import { TodoModule } from './todo/todo.module';
import { CoreModule } from './core/core.module';
import { AuthModule } from './auth/auth.module';
import { UserModule } from './user/user.module';
import {MongooseModule} from "@nestjs/mongoose";
import {db_host, db_name} from "./config";

@Module({
  imports: [
    MongooseModule.forRoot(`mongodb://${db_host}/${db_name}`),
    TodoModule,
    CoreModule,
    AuthModule,
    UserModule,
  ],
})
export class AppModule {}
