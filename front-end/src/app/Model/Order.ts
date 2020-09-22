import { OrderList } from './OrderList';

export interface Order {
   orderId:string,
   supplierId:string
   customerId:string,
   orderList: OrderList[]
   totalPrice:number,
   status:string,
   comments:string,
   rating:number
}
 