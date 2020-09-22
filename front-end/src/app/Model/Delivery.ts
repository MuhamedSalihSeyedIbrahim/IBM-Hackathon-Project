import { OrderList } from './OrderList';

export interface Delivery {
   orderId:string,
   deliveryId:string,
   supplierId:string,
   customerId:string,
   orderList: OrderList[]
   totalPrice:number,
   status:string
}
