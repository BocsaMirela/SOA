import { ApiModelProperty } from "@nestjs/swagger";

export class CreateOrderDto {
    @ApiModelProperty()
    amount: number;
    @ApiModelProperty()
    userId: string;
    @ApiModelProperty()
    productId: string;
}