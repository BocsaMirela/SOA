import {
  Body,
  ConflictException,
  Controller,
  Delete,
  Get,
  GoneException,
  NotFoundException,
  Param,
  Post,
  Put,
  UseGuards,
} from '@nestjs/common';
import { ItemService } from './item.service';
import { Item } from './item.interface';
import { CreateItem } from './create-item.dto';
import { ItemWsGateway } from './item-ws.gateway';
import { JwtAuthGuard } from '../auth/jwt-auth.guard';

@UseGuards(JwtAuthGuard)
@Controller('api/item')
export class ItemController {
  constructor(
    private readonly itemService: ItemService,
    private readonly itemWsGateway: ItemWsGateway,
  ) {
  }

  @Get()
  async findAll(): Promise<Item[]> {
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

  @Post()
  async create(@Body() dto: CreateItem): Promise<Item> {
    const item = this.itemService.create(dto);
    this.itemWsGateway.broadcast({ event: 'created', payload: item });
    return item;
  }

  @Put(':id')
  async update(@Body() item: Item): Promise<Item> {
    const dbItem = this.itemService.findOne(item.id);
    if (!dbItem) {
      throw new GoneException();
    }
    if (dbItem.version > item.version) {
      throw new ConflictException();
    }
    const updatedItem = this.itemService.update({ ...item, version: item.version + 1 });
    this.itemWsGateway.broadcast({ event: 'updated', payload: updatedItem });
    return updatedItem;
  }

  @Delete(':id')
  async delete(@Param('id') id: string): Promise<void> {
    const item = this.itemService.findOne(id);
    if (!item) {
      throw new GoneException();
    }
    this.itemService.delete(id);
    this.itemWsGateway.broadcast({ event: 'updated', payload: id });
  }
}
