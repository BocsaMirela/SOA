import {NestFactory} from '@nestjs/core';

import admin, {ServiceAccount} from 'firebase-admin';
import {AppModule} from "./app.module";
import {client_email, database_URL, private_key, project_id} from "./config";

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
    await app.listen(8879);
}

bootstrap();