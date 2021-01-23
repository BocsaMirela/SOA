import {Controller, Get, NotFoundException, Param, Post, UseGuards,} from '@nestjs/common';
import {ItemService} from './item.service';
import {JwtAuthGuard} from "../auth/jwt-auth.guard";
import {ApiOkResponse, ApiTags} from "@nestjs/swagger";
import {Item} from "./item.interface";

@UseGuards(JwtAuthGuard)
@Controller('api/item')
@ApiTags('items')
export class ItemController {
    constructor(
        private readonly itemService: ItemService,
    ) {
    }

    @Get()
    @ApiOkResponse({
        description: 'The available products.',
        type: [Item],
    })
    async findAll(): Promise<any> {
        return this.itemService.findAll();
    }
}
