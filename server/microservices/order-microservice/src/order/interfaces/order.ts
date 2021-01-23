import {ApiModelProperty} from '@nestjs/swagger';
import {OrderStatus} from '../enums/order-status.enum';
import {ProductDto} from '../dto/create-order.dto';

export class Order {
    @ApiModelProperty()
    amount: number;
    @ApiModelProperty()
    userId: string;
    @ApiModelProperty()
    status: OrderStatus;
    @ApiModelProperty()
    transactionId: string;
    @ApiModelProperty({type: 'string', format: 'date-time', example: '2018-11-21T06:20:32.232Z'})
    createdAt: Date;
    @ApiModelProperty()
    product: ProductDto;
}
