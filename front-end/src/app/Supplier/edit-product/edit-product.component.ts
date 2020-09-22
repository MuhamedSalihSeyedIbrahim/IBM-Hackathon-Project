import { Component, OnInit } from '@angular/core';
import { SupplierServiceService } from 'src/app/Services/supplier-service.service';
import { ActivatedRoute } from '@angular/router';
import { Items } from 'src/app/Model/Items';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {
  snappedProductId:string;
  item:Items;
  editProductForm:FormGroup;
  categories = ["Vegitables", "Pharmacy", "Needs"];
  selectedFile:File=null;
  isFileChanged:boolean=false;
  constructor(private supplierServiceService:SupplierServiceService,private route: ActivatedRoute,private formBuilder:FormBuilder) { }

  ngOnInit() {
    this.snappedProductId = this.route.snapshot.paramMap.get('productId');
    this.supplierServiceService.getParticularProduct(this.snappedProductId).subscribe((data)=>{
      this.item=data;
      console.log(this.item);


      this.editProductForm = this.formBuilder.group({
        productId: ['', [
          Validators.required,
        ]],
        name: ['', [
          Validators.required
        ]],
        category: ['', [
          Validators.required
        ]],
        price: ['', [
          Validators.required,
          Validators.pattern("^[0-9]*$"),
        ]],
        quantity: ['', [
          Validators.required,
          Validators.pattern("^[0-9]*$"),
        ]]
      })


    })
  }

 
  get productId() {
    return this.editProductForm.get('productId');
  }

  get name() {
    return this.editProductForm.get('name');
  }

  get category() {
    return this.editProductForm.get('category');
  }

  get price() {
    return this.editProductForm.get('price');
  }

  get quantity() {
    return this.editProductForm.get('quantity');
  }
 


  onFileSelected(event){
    this.isFileChanged=true;
    this.selectedFile=<File>event.target.files[0];
    console.log(this.selectedFile);
    }

  editProduct(){
    if(this.isFileChanged){
      this.item.image=this.selectedFile;
      this.isFileChanged=false;
    }
   this.supplierServiceService.editProduct(this.item).subscribe((data)=>{
     alert("Edited Successfully");
   })
  }


} 
