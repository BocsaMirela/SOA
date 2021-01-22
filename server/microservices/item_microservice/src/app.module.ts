import {Module} from '@nestjs/common';
import {ItemModule} from "./item/item.module";
import {AuthModule} from "./auth/auth.module";
import {ScheduleModule} from "@nestjs/schedule";
import {DeviceModule} from "./device/device.module";

@Module({
    imports: [
        ItemModule,
        AuthModule,
        DeviceModule,
        ScheduleModule.forRoot()
    ],
})
export class AppModule {
}
