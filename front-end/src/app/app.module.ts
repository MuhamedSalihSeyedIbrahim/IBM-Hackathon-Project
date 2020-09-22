import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AppRoutingModule } from './app-routing.module';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './signup/signup.component';
import { AppComponent } from './app.component';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { LoginComponent } from './login/login.component';
import { MapSearchComponent } from './map-search/map-search.component';
import { SearchComponent } from './MainMenu/search/search.component';
import { UpdateStockComponent } from './Supplier/update-stock/update-stock.component';
import { AllocateDeliveryComponent } from './Supplier/allocate-delivery/allocate-delivery.component';
import { ShowOrderDetailsComponent } from './Supplier/show-order-details/show-order-details.component';
import { AddToCartComponent } from './Customer/add-to-cart/add-to-cart.component';
import { ViewCartComponent } from './Customer/view-cart/view-cart.component';
import { TrackOrderComponent } from './Customer/track-order/track-order.component';
import { AddNewProductComponent } from './Supplier/add-new-product/add-new-product.component';
import { EditProductComponent } from './Supplier/edit-product/edit-product.component';
import { DeliveryHomeComponent } from './Delivery/delivery-home/delivery-home.component';
import { ViewMapLocationComponent } from './ViewMapLocation/view-map-location/view-map-location.component';
import { ReviewComponent } from './Customer/review/review.component';
import { ReviewFormComponent } from './Customer/review-form/review-form.component';
import { ViewReviewAdminComponent } from './ViewReview/view-review-admin/view-review-admin.component';
import { ViewReviewSupplierComponent } from './ViewReview/view-review-supplier/view-review-supplier.component';
import { MapRouteComponent } from './map-route/map-route.component';
import { HomeComponent } from './home/home.component';

const appRoutes: Routes = [
  //testing / dev----------------

  { path: 'map-route', component: MapRouteComponent },
  //-------------------------------
  
  { path: '', component: HomeComponent },
  { path: 'signup/:type', component: SignupComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'map-search/:userId', component: MapSearchComponent },
  { path: 'login', component: LoginComponent },
  { path: 'search', component: SearchComponent },
  { path: 'updateStock/:productId', component: UpdateStockComponent },
  { path: 'allocateDelivery', component: AllocateDeliveryComponent },
  { path: 'addToCart/:productId', component: AddToCartComponent },
  { path: 'viewCart', component: ViewCartComponent },
  { path: 'trackOrder', component: TrackOrderComponent },
  { path: 'addNewProduct', component: AddNewProductComponent },
  { path: 'editProduct/:productId', component: EditProductComponent },
  { path: 'deliveryHome', component: DeliveryHomeComponent },
  { path: 'review', component: ReviewComponent },
  { path: 'reviewForm/:orderId', component: ReviewFormComponent },
  { path: 'adminviewreview', component: ViewReviewAdminComponent },
  { path: 'supplierviewreview', component: ViewReviewSupplierComponent },
  { path: 'viewLocation/:orderId', component: ViewMapLocationComponent },
];

@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    MapSearchComponent,
    LoginComponent,
    SearchComponent,
    UpdateStockComponent,
    AllocateDeliveryComponent,
    ShowOrderDetailsComponent,
    AddToCartComponent,
    ViewCartComponent,
    TrackOrderComponent,
    AddNewProductComponent,
    EditProductComponent,
    DeliveryHomeComponent,
    ViewMapLocationComponent,
    ReviewComponent,
    ReviewFormComponent,
    ViewReviewAdminComponent,
    ViewReviewSupplierComponent,
    MapRouteComponent,
    HomeComponent,
  ],
  imports: [
    CommonModule,
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes, { enableTracing: true }),
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
