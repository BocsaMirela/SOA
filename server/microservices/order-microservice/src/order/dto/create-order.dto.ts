import {ApiModelProperty} from "@nestjs/swagger";

export class ProductDto {
    @ApiModelProperty()
    id: string;
    @ApiModelProperty()
    title: string;
    @ApiModelProperty()
    description: string;
    @ApiModelProperty()
    brand: string;
    @ApiModelProperty()
    price: number;
}

export class CreateOrderDto {
    @ApiModelProperty()
    amount: number;
    @ApiModelProperty()
    userId: string;
    @ApiModelProperty()
    product: ProductDto;
}
