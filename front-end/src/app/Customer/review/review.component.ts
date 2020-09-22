import { Component, OnInit } from '@angular/core';
import { CustomerServiceService } from 'src/app/Services/customer-service.service';
import { Order } from 'src/app/Model/Order';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css']
})
export class ReviewComponent implements OnInit {
reviewOrders:Order[];
  constructor(private customerServiceService:CustomerServiceService) { }

  ngOnInit(): void {
this.customerServiceService.getOrdersToReview().subscribe((data)=>{
this.reviewOrders=data;
console.log(this.reviewOrders)
})
  }

  
}
