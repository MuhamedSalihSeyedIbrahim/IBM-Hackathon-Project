import { Component, OnInit } from '@angular/core';
import { CustomerServiceService } from 'src/app/Services/customer-service.service';
import { Order } from 'src/app/Model/Order';
import { Router } from '@angular/router';

@Component({
  selector: 'app-view-cart',
  templateUrl: './view-cart.component.html',
  styleUrls: ['./view-cart.component.css']
})
export class ViewCartComponent implements OnInit {
  orders:Order[];
  constructor(private customerServiceService:CustomerServiceService,private router: Router) { }

  ngOnInit(){
    this.customerServiceService.viewCart().subscribe((data)=>{
      this.orders=data;
      console.log("Orderssss",this.orders);
    })
  }

  editQuantity(productId:string,orderId:string){
    this.customerServiceService.isUpdateCart=true;
this.customerServiceService.setOrderIdToUpdate(orderId);
    this.router.navigate(['/addToCart',productId]);

  }

  deleteOrder(productId:string,orderId:string){
    this.customerServiceService.deleteOrder(productId,orderId)
  }

  trackOrder(orderId:string){
    this.router.navigate(['/viewLocation/',orderId]);
  } 
}
