import { Injectable } from '@angular/core';
import { Items } from '../Model/Items';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Order } from '../Model/Order';
import { AuthServiceService } from './auth-service.service';
import { environment } from 'src/environments/environment';
import { UserAuthService } from './user-auth.service';



@Injectable({
  providedIn: 'root'
})



export class SupplierServiceService {

  constructor(private httpClient:HttpClient, private authService:UserAuthService) { }

  addProduct(items:Items): Observable<any> {
    let headers = new HttpHeaders();
headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
return this.httpClient.post<string>(environment.productUrl,items, { headers });
     }
     
     getParticularProduct(productId:string){
      console.log(productId);
      let headers = new HttpHeaders();
      headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
      return this.httpClient.get<Items>(environment.supplierUrl+'getProduct/'+this.authService.getUserId+"/"+
      productId, { headers });
    }

    editProduct(item:Items){
      console.log(item);
      let headers = new HttpHeaders();
      headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
      return this.httpClient.post<string>(environment.productUrl+"/update",item ,{ headers });
    }


    getViewReviewsAdmin() : Observable<any[]>{
//return this.httpClient.get<Order[]>('/assets/ViewReview.json');
  let headers = new HttpHeaders();
  headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
 return this.httpClient.get<Order[]>(environment.reviewOrderUrl + "/viewAdmin/" + this.authService.userId, { headers });
}

getViewReviewsSupplier() : Observable<any[]>{
 // return this.httpClient.get<Order[]>('/assets/ViewReview.json');
 let headers = new HttpHeaders();
 headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
return this.httpClient.get<Order[]>(environment.reviewOrderUrl + "/viewSupplier/" + this.authService.userId, { headers });
}



reviewedAdmin(orderId:string){
  console.log(orderId);
 // return this.httpClient.get<Items>("/assets/success.json");  
 let headers = new HttpHeaders();
 headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
return this.httpClient.put<String>(environment.reviewOrderUrl + "/reviewedAdmin/" + orderId , { headers });     
}

reviewedSupplier(orderId:string){
  console.log(orderId);
  //return this.httpClient.get<Items>("/assets/success.json");    
  let headers = new HttpHeaders();
  headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
 return this.httpClient.put<String>(environment.reviewOrderUrl + "/reviewedSupplier/" + orderId , { headers });   
}




}
