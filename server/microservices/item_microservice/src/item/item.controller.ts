import {Controller, Get, NotFoundException, Param, Post, UseGuards,} from '@nestjs/common';
import {ItemService} from './item.service';
import {Item} from './item.interface';
import {JwtAuthGuard} from "../auth/jwt-auth.guard";

@UseGuards(JwtAuthGuard)
@Controller('api/item')
export class ItemController {
    constructor(
        private readonly itemService: ItemService,
    ) {
    }

    @Get()
    async findAll(): Promise<any> {
        return this.itemService.findAll();
    }

    @Get(':id')
    async findOne(@Param('id') id: string): Promise<Item> {
        const item = this.itemService.findOne(id);
        if (!item) {
            throw new NotFoundException();
        }
        return item;
    }
}
