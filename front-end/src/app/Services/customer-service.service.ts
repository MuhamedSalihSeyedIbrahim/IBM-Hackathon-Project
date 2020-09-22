import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Order } from '../Model/Order';
import { AuthServiceService } from './auth-service.service';
import { environment } from 'src/environments/environment';
import { UserAuthService } from './user-auth.service';

@Injectable({
  providedIn: 'root'
})
export class CustomerServiceService {
isUpdateCart:boolean=false;

  constructor(private httpClient : HttpClient, private authService:UserAuthService) { }
public orderIdToUpdate:string;
  public setOrderIdToUpdate(orderIdToUpdate: string) {
    this.orderIdToUpdate = orderIdToUpdate;
  }
  public getOrderIdToUpdate() {
    return this.orderIdToUpdate;
  }

  
addToCart(snappedProductId:string,quantity:number): Observable<any> {
  console.log(snappedProductId,quantity);
  //return this.httpClient.get<string>("/assets/success.json");
   let headers = new HttpHeaders();
   headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
  return this.httpClient.post<void>(environment.customerOrderUrl + "/addToCart/" + this.authService.userId + '/' + snappedProductId + '/' + quantity, { headers });
}


viewCart() : Observable<any[]>{
 // return this.httpClient.get<Order[]>('/assets/Cart.json');
 let headers = new HttpHeaders();
   headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
  return this.httpClient.get<Order[]>(environment.customerOrderUrl+'/viewCart/'+  this.authService.userId, {headers});
}

  
updateCartProductQuantity(snappedProductId:string,quantity:number): Observable<any> {
  console.log(this.getOrderIdToUpdate());

  //return this.httpClient.get<string>("/assets/success.json");
   let headers = new HttpHeaders();
   headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
  return this.httpClient.put<void>(environment.customerOrderUrl + "/updateCart/" + snappedProductId + '/' +quantity+ '/'+this.authService.userId, { headers });
}

deleteOrder(productId:string,orderId:string): Observable<any> {
  console.log(productId);
  //return this.httpClient.get<string>("/assets/success.json");
   let headers = new HttpHeaders();
   headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
  return this.httpClient.delete<void>(environment.customerOrderUrl + "/deleteCartProduct/" + productId +'/'+orderId, { headers });
}


getParticularOrderToReview(orderId:string) : Observable<any>{
 // console.log("PPPPPPPPPPPPPPPPPPPPPPP",this.httpClient.get<Order>('/assets/ReviewForm.json'));
  let headers = new HttpHeaders();
   headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
  return this.httpClient.get<Order>(environment.reviewOrderUrl + "/" + orderId , { headers });
  //return this.httpClient.get<Order>('/assets/ReviewForm.json');
}
getOrdersToReview() : Observable<any[]>{
 // return this.httpClient.get<Order[]>('/assets/Review.json');
  let headers = new HttpHeaders();
   headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
  return this.httpClient.get<Order[]>(environment.reviewOrderUrl + "/reviewDeliveredOrder/" + this.authService.userId, { headers });
}


reviewSubmit(reviewOrder:Order): Observable<any> {
  console.log(reviewOrder);
 // return this.httpClient.get<string>("/assets/success.json");
   let headers = new HttpHeaders();
   headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
  return this.httpClient.put<String>(environment.reviewOrderUrl + "/reviewSubmit/" ,reviewOrder,{headers });
}



}

