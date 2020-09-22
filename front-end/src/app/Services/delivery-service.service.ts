import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Delivery } from '../Model/Delivery';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Order } from '../Model/Order';
import { AuthServiceService } from './auth-service.service';
import { UserAuthService } from './user-auth.service';

@Injectable({
  providedIn: 'root'
})
export class DeliveryServiceService {

  constructor(private httpClient : HttpClient, private authService:UserAuthService) { }
  
  getDeliveryOrder() : Observable<any[]>{
   // return this.httpClient.get<Delivery[]>('/assets/delivery.json');
    let headers = new HttpHeaders();
   headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
  return this.httpClient.get<Order[]>(environment.deliveryOrderUrl + "/" + this.authService.userId, { headers });
  }


  startDelivery(orderId:string) : Observable<any>{
    console.log(orderId);
   // return this.httpClient.get<string>("/assets/success.json");
    let headers = new HttpHeaders();
   headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
  return this.httpClient.put<String>(environment.deliveryOrderUrl + "/start/" + orderId, { headers });
    }
    
    deliveryDone(orderId:string) : Observable<any>{
      console.log(orderId);
     // return this.httpClient.get<string>("/assets/success.json");
      let headers = new HttpHeaders();
      headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
     return this.httpClient.put<String>(environment.deliveryOrderUrl + "/done/" + orderId, { headers });
      }
      


}
