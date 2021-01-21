import {NestFactory} from '@nestjs/core';

import admin, {ServiceAccount} from 'firebase-admin';
import {AppModule} from "./app.module";

async function bootstrap() {
    const app = await NestFactory.create(AppModule);

    const adminConfig: ServiceAccount = {
        "projectId": "soaitems",
        "privateKey": "-----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC79+GZGPj7up45\nROaK75NC3buCLlvMxTlTCGtxEoMQL3iWfuVtynz4lhTl4mGQtsz7lgo45a+5ENDn\nujx0ptKYE1/iGZS0dRjtsyeGm1hPLsluB+ueOx3qco3fumQ4QedN54pwmoBP8IKw\ngDG9DVG3YS6g0icXNRthS48OfzeQil3hj7n4dpdJbL8aRNVdWNj1Muy5GhsEf56d\nIwlZwGpDZX7t3/pJSiwN6MOBmWnwHGjVb1fOGUZ1l04nufh6EUFW7E0SsZ5CKJ0s\nQLZoqaRKAs7bb855Ijb78ZMiAXGUm4oRp/BCqPFi1w6tU/UzKhCxNRqIbH9Meee+\nQGVPGZY7AgMBAAECggEARwJP0XxjowVO/KeQyG/VG++UBIvq1iKzkzCdFiw+IwEr\n9v6dQaq7J1XbMzUgUoEcUvbNic0LY0h9Au2SJSdYa2b2U/4B9WI/Npz1k55f8Bnm\nspB/bFk85fK8sgZCEhajgQS1gfB8BLcBSBhFsfu0ISivasW5SsUfQNRAiON6ZDma\n0Elp6NyVFBvWx0VqzUR5R1tZlsoord9jcxf0SmR64HenrBuyxRNh75Eeu+QXQV1q\nmgBbEaqYEOS1md+/HARrjITTI5ZCLy2KaijRBs1Irn4hqyC96sAfgMY1hDcsYRjj\nU/u4yGxDNYvPzD2Y2EWXq8lkjhC2BlLOSoptfOUaMQKBgQDx81YvjGtkLyot04VF\npHm6h2Bl/osrfNb7iaV7znyw6zQtaq9Dqm53VCOzkQhBIOUHPFoc3dBZitPH3Q/I\nG1wmzopM+ahG7YdEAC7oWniSUJbzf1Lnn4hvTtuhlYgPlNzFQKTKP8oy2fGo6608\nlmrQ1DzyVdfwMu/G0/yqmb05DQKBgQDG4hVJTBvD21IcHJRRglMhCVSywWKqNak+\nZmdJZjKbvcn1GFzrI+RsK3Vg3xq7ml8btHlqJrf1y00az4quz6BIfr3ysVourd48\nKTP00GVle7K0nOEFlgR3aDP32g8fMv+OMOqH+MRxYQOeNnrY5ToB8/7/pjtC0g9t\nO1LODSmqZwKBgCRon8GL+eUbi9dViyqNs5u0H3d5tGuY36j+b56P1gexfSqQcUtX\nbEQcrzqoSnuyfYR3OoA5iM7Qq0naJDPSmi1kyz8GGqKBv+clt+lTI+2wnuaJdGpS\ngW9uiHJqVA00M3yjW6jrrHu2fD0VKf6CDBTcu0ckT9hfceGTqOPrOqRJAoGBAIF2\nSA73iRdcXD3KaM4KPNzDeywFcJmF5s6k0FVaPlVdHOr72fiC272CKJ1PfdJjMcgS\nGfr8i0Fn/qAWO5uBsj+eqT0jL6Y1LfYRiC2zvl1qQTEK8fu15OBpp3KOsstHYk2I\nEBNE33NCiJ1jr2IjDTiaQQLfjbH4lWwwcr2Imfe/AoGBAOeX2zbfOEynlgdLP7qz\nsgPuUzWEjtsSCRzVuWNC8/fjbRSPeME03TZ1N6ZJ4Yr5eBtH5MZYlWiJoADqxbsw\n9OkSR9gPJ5mlRDhp/ZTNEw7zjoONovzxQBPM7LCA95r2hh4iFqEfZQorDR1S58IB\nd2nXsMBFfxe3hvuHTC6PlNrU\n-----END PRIVATE KEY-----\n".replace(/\\n/g, '\n'),
        "clientEmail": "firebase-adminsdk-x7suc@soaitems.iam.gserviceaccount.com",
    };

    admin.initializeApp({
        credential: admin.credential.cert(adminConfig),
        databaseURL: "https://soaitems-default-rtdb.firebaseio.com"
    });


    app.enableCors();

    await app.listen(8879);
}

bootstrap();