import {Injectable} from '@nestjs/common';
import {Cron, CronExpression} from '@nestjs/schedule';
import admin from "firebase-admin";

@Injectable()
export class TasksService {

    // @Cron("0 20 * * 0")
    @Cron(CronExpression.EVERY_30_SECONDS)
    handleCron() {
        this.sendNotifications()
    }

    private async sendNotifications() {
        const payload = {
            notification: {
                title: 'New products available',
                body: "Don't miss any product! Check the updated list and buy your product!",
            }
        };

        const allTokens = await admin.firestore().collection('fcmTokens').get();
        const tokens = [];
        allTokens.forEach((tokenDoc) => {
            tokens.push(tokenDoc.id);
        });

        if (tokens.length > 0) {
            const response = await admin.messaging().sendToDevice(tokens, payload);
            await this.cleanupTokens(response, tokens);
        }
    }

    private cleanupTokens(response, tokens) {
        const tokensDelete = [];
        response.results.forEach((result, index) => {
            const error = result.error;
            if (error) {
                if (error.code === 'messaging/invalid-registration-token' || error.code === 'messaging/registration-token-not-registered') {
                    const deleteTask = admin.firestore().collection('fcmTokens').doc(tokens[index]).delete();
                    tokensDelete.push(deleteTask);
                }
            }
        });
        return Promise.all(tokensDelete);
    }
}