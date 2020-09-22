import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Order } from 'src/app/Model/Order';
import { CustomerServiceService } from 'src/app/Services/customer-service.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-review-form',
  templateUrl: './review-form.component.html',
  styleUrls: ['./review-form.component.css']
})
export class ReviewFormComponent implements OnInit {
  snappedOrderId:string;
  orderToReview:Order;
  reviewForm:FormGroup;

  constructor(private router:Router,private route: ActivatedRoute,private customerServiceService:CustomerServiceService,private formBuilder:FormBuilder) { }

  ngOnInit() {

    this.snappedOrderId = this.route.snapshot.paramMap.get('orderId');
    this.customerServiceService.getParticularOrderToReview(this.snappedOrderId).subscribe((data)=>{
      this.orderToReview=data;
      
      console.log(this.orderToReview);
  
  }
    )

        this.reviewForm = this.formBuilder.group({
        rating: ['', [
          Validators.required,
          Validators.pattern("^[0-9]*$"),
        ]],
        comments: ['', [
          Validators.required
        ]]
      })
 

}

get rating() {
  return this.reviewForm.get('rating');
}

get comments() {
  return this.reviewForm.get('comments');
}


reviewSubmit(formData:any){
  console.log(formData);
  this.orderToReview.rating=formData.rating; 
  this.orderToReview.comments=formData.comments;
  console.log(this.orderToReview);
   this.customerServiceService.reviewSubmit(this.orderToReview).subscribe((data)=>{
     alert("Done");
     this.router.navigate(['/review']);
   })
}
}

