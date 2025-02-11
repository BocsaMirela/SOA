import {Controller, Get, Logger, Inject, Post, Body, Param, HttpStatus, Res, HttpCode, UseGuards} from '@nestjs/common';
import {OrderService} from './order.service';
import {ClientProxy, MessagePattern, EventPattern} from '@nestjs/microservices';
import {ORDER_SERVICE} from './order.constants';
import {Observable, Subscription, from} from 'rxjs';
import {PaymentDetailsDto} from './dto/payment-details.dto';
import {CreateOrderDto} from './dto/create-order.dto';
import {IOrder} from './interfaces/order.interface';
import {OrderStatus} from './enums/order-status.enum';
import {Response} from 'express';
import {ApiCreatedResponse, ApiImplicitParam, ApiOkResponse, ApiUseTags} from '@nestjs/swagger';
import {JwtAuthGuard} from '../auth/jwt-auth.guard';
import {Order} from "./interfaces/order";

@Controller('orders')
@ApiUseTags('orders')
export class OrderController {
    private readonly logger = new Logger('OrderController');

    constructor(
        @Inject(ORDER_SERVICE) private readonly client: ClientProxy,
        private readonly service: OrderService
    ) {
    }

    @UseGuards(JwtAuthGuard)
    @Get(':userId')
    @ApiImplicitParam({
        name: 'userId',
        required: true,
        description: 'user ID',
    })
    @ApiCreatedResponse({
        description: 'All the user orders.',
        type: [Order],
    })
    userOrders(@Param('userId') userId: string): Observable<IOrder[]> {
        return from(this.service.findAllByUserId(userId));
    }

    @Post()
    @UseGuards(JwtAuthGuard)
    @ApiCreatedResponse({
        description: 'The record has been successfully created.',
        type: Order,
    })
    async create(@Res() res: Response, @Body() createOrderDto: CreateOrderDto) {
        if (!createOrderDto || !createOrderDto.amount || createOrderDto.amount <= 0 || !createOrderDto.userId || !createOrderDto.product)
            return res.status(HttpStatus.BAD_REQUEST).send();

        try {
            const order = await this.service.create(createOrderDto);
            return res.status(HttpStatus.CREATED).send(order);
        } catch (error) {
            this.logger.log('error in create');
            this.logger.log(JSON.stringify(error));
            return res.status(HttpStatus.BAD_REQUEST).send(JSON.stringify(error));
        }
    }

    @UseGuards(JwtAuthGuard)
    @Post(':id/buy')
    @ApiImplicitParam({
        name: 'id',
        required: true,
        description: 'Order ID',
    })
    async buy(@Res() res: Response, @Param('id') id: string) {
        if (!id)
            return res.status(HttpStatus.BAD_REQUEST).send();

        const order = await this.service.findById(id);
        if (!order)
            return res.status(HttpStatus.NOT_FOUND).send();

        try {
            await this.service.initiatePayment(id);
            return res.status(HttpStatus.CREATED).send(order);
        } catch (error) {
            this.logger.log('error in create');
            this.logger.log(JSON.stringify(error));
            return res.status(HttpStatus.BAD_REQUEST).send(JSON.stringify(error));
        }
    }

    @UseGuards(JwtAuthGuard)
    @Get(':id')
    @ApiImplicitParam({
        name: 'id',
        required: true,
        description: 'Order ID',
    })
    async details(@Res() res: Response, @Param('id') id: string) {
        if (!id)
            return res.status(HttpStatus.BAD_REQUEST).send();

        const order = await this.service.findById(id);
        if (!order)
            return res.status(HttpStatus.NOT_FOUND).send();

        return res.send(order);
    }

    @Get(':id/status')
    @UseGuards(JwtAuthGuard)
    @ApiImplicitParam({
        name: 'id',
        required: true,
        description: 'Order ID',
    })
    async status(@Res() res: Response, @Param('id') id: string) {
        if (!id)
            return res.status(HttpStatus.BAD_REQUEST).send();

        const order = await this.service.findById(id);
        if (!order)
            return res.status(HttpStatus.NOT_FOUND).send();

        return res.send(order.status);
    }

    @Post(':id/cancel')
    @UseGuards(JwtAuthGuard)
    @ApiImplicitParam({
        name: 'id',
        required: true,
        description: 'Order ID',
    })
    async cancel(@Res() res: Response, @Param('id') id: string) {
        try {
            const order = await this.service.cancel(id);
            return res.send(order);
        } catch (error) {
            this.logger.log(error);
            return res.status(HttpStatus.BAD_REQUEST).send(error);
        }
    }

    @EventPattern('orderCreated')
    async orderCreated(id: string) {
        await this.service.initiatePayment(id);
    }

    @EventPattern('paymentProcessed')
    async paymentProcessed(data: PaymentDetailsDto) {
        console.log('buy order payment processed', data);
        const order = await this.service.updatePaymentStatus(data);

        if (order && order.status == OrderStatus.Confirmed)
            this.client.emit('orderConfirmed', order.id);
    }

    @EventPattern('orderConfirmed')
    async orderConfirmed(id: string) {
        await this.service.deliver(id);
    }
}
