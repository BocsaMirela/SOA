import {Injectable} from '@nestjs/common';
import {Item} from './item.interface';
import admin from 'firebase-admin';
import {CreateItem} from "./create-item.dto";

@Injectable()
export class ItemService {
    private readonly firestore = admin.firestore();

    async findAll(): Promise<any> {
        const itemsRef = this.firestore.collection("items");
        const snapshot = await itemsRef.get();
        if (snapshot.empty) {
            return [];
        }
        return snapshot.docs.map(d => ItemService.createItem(d.id, d.data() as CreateItem))
    }

    async findOne(id: string): Promise<Item> {
        const itemsRef = this.firestore.collection("items").doc(id);
        const snapshot = await itemsRef.get();
        return snapshot.data() as Item
    }

    private static createItem(id: string, dto: CreateItem): Item {
        return {
            id: id,
            title: dto.title,
            description: dto.description,
            brand: dto.brand,
            price: dto.price,
        }
    }
}
