import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable} from 'rxjs';
import { Items } from '../Model/Items';
import { Order } from '../Model/Order';
import { AuthServiceService } from './auth-service.service';
import { environment } from 'src/environments/environment';
import { UserAuthService } from './user-auth.service';

@Injectable({
  providedIn: 'root',
})
export class MenuServiceService {
items:Items[];
showOrder:string=null;
  constructor(private httpClient : HttpClient, private userAuthService:UserAuthService) { 
}

getMenuitems() : Observable<any[]>{
  let headers = new HttpHeaders();
  headers = headers.set('Authorization', 'Bearer ' + this.userAuthService.getToken());
 return this.httpClient.get<Items[]>(environment.supplierUrl+"getAllProducts/"+this.userAuthService.getUserId, { headers });
}

updateStock(snappedProductId:string,quantity:number): Observable<any> {
  let headers = new HttpHeaders();
  headers = headers.set('Authorization', 'Bearer ' + this.userAuthService.getToken());
 return this.httpClient.post<String>(environment.supplierUrl+"updateQuantity/increase/"+this.userAuthService.getUserId
 +"/"+snappedProductId+"/"+quantity, { headers });
}



getOrdersForSupplier() : Observable<any[]>{
  //return this.httpClient.get<Order[]>('/assets/Order.json');
  let headers = new HttpHeaders();
  headers = headers.set('Authorization', 'Bearer ' + this.userAuthService.getToken());
 return this.httpClient.get<Order[]>(environment.supplierOrderUrl + "/getSupplierOrder/" + this.userAuthService.userId, { headers });
  //return this.httpClient.get<Order[]>('/assets/Order.json');

}


allocateOrder(orderId:string) : Observable<any>{
console.log(orderId);
//return this.httpClient.get<string>("/assets/success.json");
let headers = new HttpHeaders();
headers = headers.set('Authorization', 'Bearer ' + this.userAuthService.getToken());
return this.httpClient.put<String>(environment.supplierOrderUrl + "/allocateOrder/" + orderId , { headers });
//return this.httpClient.get<string>("/assets/success.json");
}


getOrderToPlotInMap(orderId:String) : Observable<any>{
  //return this.httpClient.get<Order>('/assets/ReviewForm.json');
  let headers = new HttpHeaders();
  headers = headers.set('Authorization', 'Bearer ' + this.userAuthService.getToken());
 return this.httpClient.get<String>(environment.customerOrderUrl + "/customerCoordinates/" + orderId, { headers });
 // return this.httpClient.get<Order>('/assets/ReviewForm.json');

}
}
