import { Component, OnInit } from '@angular/core';
import { MenuServiceService } from 'src/app/Services/menu-service.service';
import { Order } from 'src/app/Model/Order';


@Component({
  selector: 'app-allocate-delivery',
  templateUrl: './allocate-delivery.component.html',
  styleUrls: ['./allocate-delivery.component.css']
})
export class AllocateDeliveryComponent implements OnInit {

  orders:Order[];MenuServiceService

  constructor(public menuServiceService:MenuServiceService) { }
 
  ngOnInit(){

    this.menuServiceService.getOrdersForSupplier().subscribe((data)=>{
      this.orders=data;
      console.log(this.orders);
    })
  }


  viewOrder(orderId:string){
    this.menuServiceService.showOrder=orderId;
 
   
  }
  closeOrder(){
    this.menuServiceService.showOrder=null;
  }

  allocateOrder(orderId:string){
this.menuServiceService.allocateOrder(orderId).subscribe((data)=>{
  alert(JSON.stringify(data));
});
  }
}
