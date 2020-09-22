import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MenuServiceService } from 'src/app/Services/menu-service.service';
import { Order } from 'src/app/Model/Order';

@Component({
  selector: 'app-view-map-location',
  templateUrl: './view-map-location.component.html',
  styleUrls: ['./view-map-location.component.css']
})
export class ViewMapLocationComponent implements OnInit {
  snappedOrderId:string;
orderToPlot:Order
  constructor(private route: ActivatedRoute,private menuServiceService:MenuServiceService) { }

  ngOnInit() {
    this.snappedOrderId = this.route.snapshot.paramMap.get('orderId');
    this.menuServiceService.getOrderToPlotInMap(this.snappedOrderId).subscribe((data)=>{
     this. orderToPlot=data;
     console.log(this.orderToPlot)
    })
  }

}
