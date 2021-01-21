import {Module} from '@nestjs/common';
import {ItemModule} from "./item/item.module";
import {AuthModule} from "./auth/auth.module";

@Module({
    imports: [
        ItemModule,
        AuthModule,
    ],
})
export class AppModule {
}
