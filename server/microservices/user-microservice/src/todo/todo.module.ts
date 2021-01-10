import { Module } from '@nestjs/common';
import { ItemController } from './item.controller';
import { ItemService } from './item.service';
import { InMemoryDBModule } from '@nestjs-addons/in-memory-db';
import { ItemWsGateway } from './item-ws.gateway';

@Module({
  imports: [InMemoryDBModule.forFeature('item', {})],
  controllers: [ItemController],
  providers: [ItemService, ItemWsGateway]
})
export class TodoModule {}
