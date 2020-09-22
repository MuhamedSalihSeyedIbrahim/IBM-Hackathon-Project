import { Component, OnInit } from '@angular/core';
import { DeliveryServiceService } from 'src/app/Services/delivery-service.service';
import { Delivery } from 'src/app/Model/Delivery';
import { MenuServiceService } from 'src/app/Services/menu-service.service';

@Component({
  selector: 'app-delivery-home',
  templateUrl: './delivery-home.component.html',
  styleUrls: ['./delivery-home.component.css']
})
export class DeliveryHomeComponent implements OnInit {
  deliveryOrders:Delivery[];

  constructor(private deliveryServiceService:DeliveryServiceService,public menuServiceService:MenuServiceService) { }

  ngOnInit(){
this.deliveryServiceService.getDeliveryOrder().subscribe((data)=>{
this.deliveryOrders=data;
console.log(this.deliveryOrders);

})
  }

  startDelivery(orderId:string){
    this.deliveryServiceService.startDelivery(orderId).subscribe((data)=>{
      alert(JSON.stringify(data));
    })
  }

  viewOrder(orderId:string){
    this.menuServiceService.showOrder=orderId;
  
   
  } 


  
  deliveryDone(orderId:string){
    this.deliveryServiceService.deliveryDone(orderId).subscribe((data)=>{
      alert(JSON.stringify(data));
    })
  }

  closeOrder(){
    this.menuServiceService.showOrder=null;
  }

}

