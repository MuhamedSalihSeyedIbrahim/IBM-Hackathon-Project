import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UpdateStock } from 'src/app/Model/UpdateStock';
import { MenuServiceService } from 'src/app/Services/menu-service.service';

@Component({
  selector: 'app-update-stock',
  templateUrl: './update-stock.component.html',
  styleUrls: ['./update-stock.component.css']
})
export class UpdateStockComponent implements OnInit {
   updateForm: FormGroup;
   snappedProductId:string;

  constructor(private route: ActivatedRoute,private formBuild: FormBuilder,private router: Router,private menuServiceService :MenuServiceService) { }

  ngOnInit(){
     this.snappedProductId = this.route.snapshot.paramMap.get('productId');
    this.updateForm = this.formBuild.group({
      updateQuantity: ['', [
        Validators.required 
      ]],
    })
   }


   get updateQuantity() {
    return this.updateForm.get('updateQuantity');
  }


  updateStock(quantity:number){

    //this.stockUpdate.productQuantity=quantity;
console.log('KKKKKKKKKKKK',quantity,this.snappedProductId);
this.menuServiceService.updateStock(this.snappedProductId,quantity).subscribe(
  data => {
    alert('updated successfully');
  }
);
this.router.navigate(['search']);
}

  }

