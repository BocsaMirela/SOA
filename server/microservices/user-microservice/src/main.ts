import {NestFactory} from '@nestjs/core';
import {AppModule} from './app.module';
import {WsAdapter} from '@nestjs/platform-ws';
import {MicroserviceOptions, Transport} from '@nestjs/microservices';
import {DocumentBuilder, SwaggerModule} from '@nestjs/swagger';

async function bootstrap() {
    const app = await NestFactory.create(AppModule);
    app.connectMicroservice<MicroserviceOptions>({
        transport: Transport.TCP,
        options: {retryAttempts: 5, retryDelay: 3000},
    });
    await app.startAllMicroservicesAsync();

    app.enableCors();
    app.useWebSocketAdapter(new WsAdapter(app));

    const options = new DocumentBuilder()
        .setTitle('User Service')
        .setDescription('Manages users and authentication')
        .setVersion('1.0')
        .addTag('auth')
        .setBasePath('/api')
        .build();
    const document = SwaggerModule.createDocument(app, options);
    SwaggerModule.setup('doc', app, document);

    await app.listen(8879);
}

bootstrap();
