import { Module } from '@nestjs/common';
import {DeviceController} from './device.controller';
import { DeviceService } from './device.service';
import {TasksService} from "./task.service";

@Module({
  controllers: [DeviceController],
  providers: [DeviceService, TasksService]
})
export class DeviceModule {}
