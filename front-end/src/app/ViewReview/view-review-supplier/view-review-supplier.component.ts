import { Component, OnInit } from '@angular/core';
import { Order } from 'src/app/Model/Order';
import { SupplierServiceService } from 'src/app/Services/supplier-service.service';
import { MenuServiceService } from 'src/app/Services/menu-service.service';

@Component({
  selector: 'app-view-review-supplier',
  templateUrl: './view-review-supplier.component.html',
  styleUrls: ['./view-review-supplier.component.css']
})
export class ViewReviewSupplierComponent implements OnInit {
  reviews:Order[]
  constructor(private supplierServiceService:SupplierServiceService,public menuServiceService:MenuServiceService) { }

  ngOnInit() {
    this.supplierServiceService.getViewReviewsSupplier().subscribe((data)=>{
      this.reviews=data;
    })

  }

  viewOrder(orderId:string){
    this.menuServiceService.showOrder=orderId;
 
   
  }
  closeOrder(){
    this.menuServiceService.showOrder=null;
  }

    reviewedSupplier(orderId:string){
  this.supplierServiceService.reviewedSupplier(orderId).subscribe((data)=>{
    alert("DONE");
  })

}

}
 