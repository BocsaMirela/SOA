import {NestFactory} from '@nestjs/core';
import {AppModule} from './app.module';
import {WsAdapter} from '@nestjs/platform-ws';
import {MicroserviceOptions, Transport} from '@nestjs/microservices';

async function bootstrap() {
    const app = await NestFactory.create(AppModule);
    app.connectMicroservice<MicroserviceOptions>({
        transport: Transport.TCP,
        options: {retryAttempts: 5, retryDelay: 3000},
    });
    await app.startAllMicroservicesAsync();

    app.enableCors();
    app.useWebSocketAdapter(new WsAdapter(app));
    await app.listen(8874);
}

bootstrap();
