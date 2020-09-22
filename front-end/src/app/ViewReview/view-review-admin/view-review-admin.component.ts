import { Component, OnInit } from '@angular/core';
import { Order } from 'src/app/Model/Order';
import { SupplierServiceService } from 'src/app/Services/supplier-service.service';
import { MenuServiceService } from 'src/app/Services/menu-service.service';

@Component({
  selector: 'app-view-review-admin',
  templateUrl: './view-review-admin.component.html',
  styleUrls: ['./view-review-admin.component.css']
})
export class ViewReviewAdminComponent implements OnInit {
reviews:Order[]
  constructor(private supplierServiceService:SupplierServiceService,public menuServiceService:MenuServiceService) { }

  ngOnInit(){
    this.supplierServiceService.getViewReviewsAdmin().subscribe((data)=>{
      this.reviews=data;
    })
  }


  viewOrder(orderId:string){
    this.menuServiceService.showOrder=orderId;
 
   
  }
  closeOrder(){
    this.menuServiceService.showOrder=null;
  }

  reviewedAdmin(orderId:string)
  {
    this.supplierServiceService.reviewedAdmin(orderId).subscribe((data)=>{
      alert("DONE");
    })

  }
}
 