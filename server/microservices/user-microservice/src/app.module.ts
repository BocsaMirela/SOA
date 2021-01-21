import { Module } from '@nestjs/common';
import { CoreModule } from './core/core.module';
import { AuthModule } from './auth/auth.module';
import { UserModule } from './user/user.module';
import {MongooseModule} from "@nestjs/mongoose";
import {db_host, db_name} from "./config";

@Module({
  imports: [
    MongooseModule.forRoot(`mongodb://${db_host}/${db_name}`),
    CoreModule,
    AuthModule,
    UserModule,
  ],
})
export class AppModule {}
