import * as mongoose from 'mongoose';
import {OrderStatus} from '../enums/order-status.enum';

export const OrderSchema = new mongoose.Schema({
    amount: Number,
    userId: {type: String},
    product: {
        id: {type: String},
        title: {type: String},
        description: {type: String},
        brand: {type: String},
        price: Number,
    },
    status: {type: String, default: OrderStatus.Created},
    transactionId: String,
    createdAt: {type: Date, default: Date.now},
});
