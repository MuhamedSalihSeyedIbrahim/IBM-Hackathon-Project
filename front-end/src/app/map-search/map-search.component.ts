import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthServiceService } from '../Services/auth-service.service';
import { ThrowStmt } from '@angular/compiler';
import { User } from '../Model/User';
import { environment } from './../../environments/environment';

declare var H: any;

@Component({
  selector: 'app-map-search',
  templateUrl: './map-search.component.html',
  styleUrls: ['./map-search.component.css'],
})
export class MapSearchComponent implements OnInit {
  //nearest current coordinate if not null
  public location: any;

  //nearest market coordinate if not {}
  public nearestMarketLocation: any = {};

  //nearest current coordinate if not null
  public address: any = {};

  private platform: any;

  private map: any;

  private behavior: any;

  private userId: any;

  public user: any;

  @ViewChild('map')
  public mapElement: ElementRef;

  public JSON: any;

  public Object: any;

  public constructor(
    private route: ActivatedRoute,
    private authService: AuthServiceService
  ) {
    this.Object = Object;
    this.JSON = JSON;
    this.platform = new H.service.Platform({
      apikey: environment.apiKey,
    });
  }

  mapCenter(coordinates, zoomLevel = 8) {
    this.map.setCenter(coordinates);
    this.map.setZoom(zoomLevel);
  }

  findNearestMarker(coordinates, radius = 10000) {
    let minDist = radius;

    let markerDist;
    // get all objects added to the map
    let marketObjects = this.getMapSpecificObject('marketMarker');
    let len = marketObjects.length;

    // iterate over marketObjects and calculate distance between them
    for (let i = 0; i < len; i += 1) {
      markerDist = marketObjects[i].getGeometry().distance(coordinates);
      if (markerDist < minDist) {
        minDist = markerDist;
        console.log({
          ...marketObjects[i].getGeometry(),
          Data: marketObjects[i].getData(),
        });
        this.nearestMarketLocation = { ...marketObjects[i].getGeometry() };
      }
    }
  }

  getMapSpecificObject(specificObject) {
    let id = specificObject;
    let mapObjects = this.map.getObjects();
    return mapObjects.filter((mapObject) => {
      return mapObject.id === id;
    });
  }

  addMarketMarkersToMap(map, coordinates) {
    
    //when marking next set of market remove past marking
    this.removeMapObject(this.map,"marketMarker");

    //null safety
    if (!coordinates) {
      coordinates = [];
    }

    coordinates.forEach(function (coords, index) {

      //null safe
      if (!coords) return;

      //exract coord to map from supplier object
      let { coordinate } = coords;

      let svgMarkup =
        '<svg  width="24" height="24" xmlns="http://www.w3.org/2000/svg">' +
        '<rect stroke="black" fill="${FILL}" x="1" y="1" width="22" height="22" />' +
        '<text x="12" y="18" font-size="12pt" font-family="Arial" font-weight="bold" ' +
        'text-anchor="middle" fill="${STROKE}" >${Content}</text></svg>';

      let myIcon = new H.map.Icon(
        svgMarkup
          .replace('${FILL}', 'blue')
          .replace('${STROKE}', 'red')
          .replace('${Content}', (index + 1).toString()),
        {
          anchor: { x: 12, y: 12 },
        }
      );

      let marketMarker = new H.map.Marker(coordinate, {
        icon: myIcon,
        volatility: true,
      });
      // add custom data to the marker
      marketMarker.id = 'marketMarker';
      marketMarker.setData({"index":index + 1,...coords});
      map.addObject(marketMarker);
    });
  }

  removeMapObject(map, specificObject = null) {
    if (specificObject) {
      let id = specificObject;
      for (let object of map.getObjects()) {
        if (object.id === id) {
          map.removeObject(object);
        }
      }
      return;
    }
    // to remove all previous object
    map.removeObjects(map.getObjects());
  }

  addRadiusToMapMarker(map, coordinates) {
    let currentRadius = new H.map.Circle(
      // The central point of the circle
      coordinates,
      // The radius of the circle in meters
      10000,
      {
        style: {
          strokeColor: 'rgba(55, 85, 170, 0.6)', // Color of the perimeter
          lineWidth: 2,
          fillColor: 'rgba(0, 128, 0, 0.7)', // Color of the circle
        },
      }
    );
    currentRadius.id = 'circle';
    map.addObject(currentRadius);
  }

  public async getLocation() {
    if (navigator.geolocation) {
      return new Promise((res, rej) => {
        navigator.geolocation.getCurrentPosition(
          (position: any): void =>
            res({
              lat: position.coords.latitude,
              lng: position.coords.longitude,
            }),
          (err: any): void =>
            res({ lat: 111.127122499999999, lng: 178.6568942 })
        );
      });
    }
  }

  addDraggableMarker(map, behavior, coordinates) {
    //update location
    this.location = coordinates;

    //remove map objects
    this.removeMapObject(map, 'marker');
    //this.removeMapObject(map, 'circle');

    //radius Marker
    //this.addRadiusToMapMarker(map, coordinates);

    //Map Centering for new Marked Point
    this.mapCenter(coordinates);

    // new marker intialisation
    var marker = new H.map.Marker(coordinates, {
      volatility: true,
    });

    marker.draggable = true;
    marker.id = 'marker';
    map.addObject(marker);

    map.addEventListener(
      'dragstart',
      (ev) => {
        var target = ev.target,
          pointer = ev.currentPointer;
        if (target instanceof H.map.Marker) {
          var targetPosition = map.geoToScreen(target.getGeometry());
          target['offset'] = new H.math.Point(
            pointer.viewportX - targetPosition.x,
            pointer.viewportY - targetPosition.y
          );
          behavior.disable();

          //To Clear the Previous Nearest Market Coordinate
          this.nearestMarketLocation = {};
        }
      },
      false
    );

    map.addEventListener(
      'dragend',
      (ev) => {
        let target = ev.target;
        let pointer = ev.currentPointer;
        if (target instanceof H.map.Marker) {
          behavior.enable();

          this.location = {
            ...map.screenToGeo(
              pointer.viewportX - target['offset'].x,
              pointer.viewportY - target['offset'].y
            ),
          };
          // this.removeMapObject(map, 'circle');
          // this.addRadiusToMapMarker(this.map, this.location);
          //find the Nearest points
          this.findNearestMarker(this.location);
          this.reverseGeocode(this.location);
        }
      },
      false
    );

    map.addEventListener(
      'drag',
      (ev) => {
        var target = ev.target,
          pointer = ev.currentPointer;
        if (target instanceof H.map.Marker) {
          target.setGeometry(
            map.screenToGeo(
              pointer.viewportX - target['offset'].x,
              pointer.viewportY - target['offset'].y
            )
          );
        }
      },
      false
    );

    //find the Nearest points
    this.findNearestMarker(coordinates);
    this.reverseGeocode(coordinates);
  }

  reverseGeocode(coordinates) {
    // Get an instance of the search service:
    let service = this.platform.getSearchService();

    // Call the reverse geocode method with the geocoding parameters,
    // the callback and an error callback function (called if a
    // communication error occurs):
    service.reverseGeocode(
      {
        at: coordinates.lat.toString() + ',' + coordinates.lng.toString(),
      },
      (result) => {


        

        result.items.forEach((item) => {
          this.address = item.address;

          //using pincode get all nearest Markets
          let nearestMarketCordinates=  this.authService.getSuppliers(this.address.postalCode);
          this.addMarketMarkersToMap(this.map, nearestMarketCordinates);
        });
      },
      (err) => {
        console.log(err);
      }
    );
  }

  markLocation(value: string) {
    let service = this.platform.getSearchService();

    service.geocode(
      {
        q: value,
      },
      (result) => {
        // Add a marker for each location found
        result.items.forEach((item) => {
          this.address = item.address;
           
          //using pincode get all nearest Markets
           let nearestMarketCordinates=  this.authService.getSuppliers(this.address.postalCode);
           this.addMarketMarkersToMap(this.map, nearestMarketCordinates);

          //draggable marker
          this.addDraggableMarker(this.map, this.behavior, item.position);
        });
      },
      (err) => {
        this.addDraggableMarker(this.map, this.behavior, this.location);
        console.log(err);
      }
    );
  }

  public ngOnInit() {
   // this.user = this.route.snapshot.paramMap.get('user');
   // this.userId = this.user.userId;
   this.userId =null;
  }

  public async ngAfterViewInit() {
    this.location = await this.getLocation();

    let defaultLayers = this.platform.createDefaultLayers();
    this.map = new H.Map(
      this.mapElement.nativeElement,
      defaultLayers.vector.normal.map,
      {
        zoom: 8,
        center: this.location,
      }
    );
    window.addEventListener('resize', () => this.map.getViewPort().resize());

    this.behavior = new H.mapevents.Behavior(
      new H.mapevents.MapEvents(this.map)
    );
    var ui = H.ui.UI.createDefault(this.map, defaultLayers);

    //draggable marker
    this.removeMapObject(this.map, 'marker');
    //this.removeMapObject(this.map, 'circle');
    this.addDraggableMarker(this.map, this.behavior, this.location);
  }

  signup() {
    if (
      !this.location ||
      !this.nearestMarketLocation.lat ||
      !this.nearestMarketLocation.lng
    ) {
      alert('Service Not Available');
      return;
    }
    this.user.coordinates = this.location;
    this.authService.signup(this.user);
  }
}
