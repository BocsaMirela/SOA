import {Injectable} from '@nestjs/common';
import {Item} from './item.interface';
import admin from 'firebase-admin';
import {CreateItem} from "./create-item.dto";

@Injectable()
export class ItemService {

    async findAll(): Promise<any> {
        const itemsRef = admin.firestore().collection("items");
        const snapshot = await itemsRef.get();
        if (snapshot.empty) {
            return [];
        }
        return snapshot.docs.map(d => this.create(d.id, d.data() as CreateItem))
    }

    async findOne(id: string): Promise<Item> {
        const itemsRef = admin.firestore().collection("items").doc(id);
        const snapshot = await itemsRef.get();
        return snapshot.data() as Item
    }

    private create(id: string, dto: CreateItem): Item {
        return {
            id: id,
            title: dto.title,
            description: dto.description,
            brand: dto.brand,
            price: dto.price,
        }
    }
}
