import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CustomerServiceService } from 'src/app/Services/customer-service.service';

@Component({
  selector: 'app-add-to-cart',
  templateUrl: './add-to-cart.component.html',
  styleUrls: ['./add-to-cart.component.css']
})
export class AddToCartComponent implements OnInit {


  addToCartForm: FormGroup;
  snappedProductId:string;

  constructor(private route: ActivatedRoute,private formBuild: FormBuilder,private router: Router,public customerServiceService:CustomerServiceService) { }

  ngOnInit(){

       this.snappedProductId = this.route.snapshot.paramMap.get('productId');
    this.addToCartForm = this.formBuild.group({
      addToCartQuantity: ['', [
        Validators.required 
      ]],
    })
 

  }

  get addToCartQuantity() {
    return this.addToCartForm.get('addToCartQuantity');
  }



  addToCart(quantity:number){
if(!this.customerServiceService.isUpdateCart){
this.customerServiceService.addToCart(this.snappedProductId,quantity).subscribe(
  data => {
    alert('Added successfully');
  }
);
this.router.navigate(['search']);
}
if(this.customerServiceService.isUpdateCart){

  this.customerServiceService.updateCartProductQuantity(this.snappedProductId,quantity).subscribe(
    data => {

      alert('Updated successfully');

    }
  );
  this.customerServiceService.isUpdateCart=false;
  this.router.navigate(['viewCart']);

}
  }
 
}
