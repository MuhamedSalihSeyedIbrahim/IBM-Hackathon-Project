import { Component, OnInit, Pipe} from '@angular/core';
import { Items } from 'src/app/Model/Items';
import { MenuServiceService } from 'src/app/Services/menu-service.service';
import { AuthServiceService } from 'src/app/Services/auth-service.service';
import { UserAuthService } from 'src/app/Services/user-auth.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

items:Items[];
itemsFiltered:Items[];
itemSearch: string;
  
constructor(private menuServiceService:MenuServiceService,public authServiceService:AuthServiceService, public userAuthService : UserAuthService) { }
 
  ngOnInit(){
   this.menuServiceService.getMenuitems().subscribe((data)=>{
       console.log(data);
       this.items=data;
       this.itemsFiltered=this.items;
  });
 
  } 


  searchItem(){
    this.itemsFiltered=this.items.filter(item=>item.name.toLocaleLowerCase().startsWith(this.itemSearch.toLocaleLowerCase(),0));
    console.log("KKJHGG",this.itemsFiltered);
  }
}
