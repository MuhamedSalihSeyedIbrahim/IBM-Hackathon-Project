import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Items } from 'src/app/Model/Items';
import { SupplierServiceService } from 'src/app/Services/supplier-service.service';

@Component({
  selector: 'app-add-new-product',
  templateUrl: './add-new-product.component.html',
  styleUrls: ['./add-new-product.component.css']
})
export class AddNewProductComponent implements OnInit {
  addProductForm:FormGroup;

selectedFile:File=null;

  categories = ["Vegitables", "Pharmacy", "Needs"];

  constructor(private formBuilder:FormBuilder,private supplierServiceService:SupplierServiceService) { }

  ngOnInit(){

    this.addProductForm = this.formBuilder.group({
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
  }

  get productId() {
    return this.addProductForm.get('productId');
  }

  get name() {
    return this.addProductForm.get('name');
  }

  get category() {
    return this.addProductForm.get('category');
  }

  get price() {
    return this.addProductForm.get('price');
  }

  get quantity() {
    return this.addProductForm.get('quantity');
  }
 
  onFileSelected(event){
  this.selectedFile=<File>event.target.files[0];

  console.log(this.selectedFile);
  }


  addProduct(items:Items){
console.log("LLLLLLL");
items.image=this.selectedFile;
this.supplierServiceService.addProduct(items).subscribe((data)=>{
alert("added successfully")
})
  }

}
