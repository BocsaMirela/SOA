import {Injectable} from '@nestjs/common';
import {Item} from './item.interface';
import {CreateItem} from './create-item.dto';
import {InjectInMemoryDBService, InMemoryDBService} from '@nestjs-addons/in-memory-db';

@Injectable()
export class ItemService {
    constructor(@InjectInMemoryDBService('item') private readonly db: InMemoryDBService<Item>) {
        this.create({title: 'Face Cream', description: 'Day face moisturizer for dry skin', brand: 'Avenue Cosmetics', price: 50});
        this.create({
            title: 'Body milk', description: 'Body milks are perfect if you like to feel moisturised, but prefer a subtler touch. You’ll be ' +
                'left beautifully perfumed with essential oils and ' +
                'softened by ingredients such as organic extra virgin olive oil and aloe vera. ', brand: 'Nivea', price: 500
        });
        this.create({
            title: 'Mascara', description: 'Sky High lengthening and volume mascara for sky high lash impact from every angle.' +
                ' Long lasting mascara delivers full volume and limitless length.', brand: 'Maybelline', price: 100
        });
    }

    findAll(): Item[] {
        return this.db.getAll();
    }

    findOne(id: string): Item {
        return this.db.get(id);
    }

    create(dto: CreateItem): Item {
        const item = {
            title: dto.title,
            description: dto.description,
            brand: dto.brand,
            price: dto.price,
            version: 1,
        };
        return this.db.create(item);
    }

    update(item: Item): Item {
        this.db.update(item);
        return item;
    }


    delete(id: string) {
        this.db.delete(id);
    }
}
