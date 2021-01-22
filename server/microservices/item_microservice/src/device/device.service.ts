import {Injectable} from '@nestjs/common';
import admin from 'firebase-admin';
import {CreateToken} from "./create-token.dto";

@Injectable()
export class DeviceService {
    private readonly firestore = admin.firestore();

    async add(fcmToken: CreateToken) {
        return await this.firestore.collection("fcmTokens").doc(fcmToken.token).set({});
    }
}
