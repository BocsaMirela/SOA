import {NestFactory} from '@nestjs/core';

import admin, {ServiceAccount} from 'firebase-admin';
import {AppModule} from "./app.module";
import {client_email, database_URL, private_key, project_id} from "./config";
import {DocumentBuilder, SwaggerModule} from '@nestjs/swagger';

async function bootstrap() {
    const adminConfig: ServiceAccount = {
        "projectId": project_id,
        "privateKey": private_key,
        "clientEmail": client_email,
    };

    admin.initializeApp({
        credential: admin.credential.cert(adminConfig),
        databaseURL: database_URL
    });

    const app = await NestFactory.create(AppModule);
    app.enableCors();

    const options = new DocumentBuilder()
        .setTitle('Products Service')
        .setDescription('Manages available products')
        .setVersion('1.0')
        .setBasePath('/api')
        .build();
    const document = SwaggerModule.createDocument(app, options);
    SwaggerModule.setup('doc', app, document);

    await app.listen(8874);
}

bootstrap();