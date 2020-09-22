import { Component, OnInit, ViewChild, ElementRef, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthServiceService } from '../Services/auth-service.service';
import { ThrowStmt } from '@angular/compiler';
import { environment } from './../../environments/environment';


declare let H: any;

@Component({
  selector: 'app-map-route',
  templateUrl: './map-route.component.html',
  styleUrls: ['./map-route.component.css'],
})
export class MapRouteComponent implements OnInit {
  //nearest current coordinate if not null
  public location: any;

  private platform: any;

  private map: any;

  private behavior: any;

  private userId: any;

  //destination coordinates
  @Input() destinationCoorinate:any;

  @ViewChild('map')
  public mapElement: ElementRef;

  public constructor(
    private route: ActivatedRoute,
    private authService: AuthServiceService
  ) {
    this.platform = new H.service.Platform({
      apikey: environment.apiKey,
    });
  }

  mapCenter(coordinates, zoomLevel = 8) {
    this.map.setCenter(coordinates);
    this.map.setZoom(zoomLevel);
  }

  getMapSpecificObject(specificObject) {
    let id = specificObject;
    let mapObjects = this.map.getObjects();
    return mapObjects.filter((mapObject) => {
      return mapObject.id === id;
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

  addMarker(map, coordinates) {
    //update location
    this.location = coordinates;

    //remove map objects
    this.removeMapObject(map, 'marker');

    //Map Centering for new Marked Point
    this.mapCenter(coordinates);

    // new marker intialisation
    let marker = new H.map.Marker(coordinates, {
      volatility: true,
    });

    marker.id = 'marker';
    map.addObject(marker);
  }

  routeInit(startCoordinate, endCoorinate) {
    // Create the parameters for the routing request:
    let routingParameters = {
      // The routing mode:
      mode: 'fastest;car',
      // The start point of the route:
      waypoint0: `geo!${startCoordinate.lat},${startCoordinate.lng}`,
      // The end point of the route:
      waypoint1: `geo!${endCoorinate.lat},${endCoorinate.lng}`,
      // To retrieve the shape of the route we choose the route
      // representation mode 'display'
      representation: 'display',
    };

    // Get an instance of the routing service:
    let router = this.platform.getRoutingService();

    // Call calculateRoute() with the routing parameters,
    // the callback and an error callback function (called if a
    // communication error occurs):
    router.calculateRoute(
      routingParameters,
      (result) => {
        let route, routeShape, startPoint, endPoint, linestring;
        if (result.response.route) {
          // Pick the first route from the response:
          route = result.response.route[0];
          // Pick the route's shape:
          routeShape = route.shape;

          // Create a linestring to use as a point source for the route line
          linestring = new H.geo.LineString();

          // Push all the points in the shape into the linestring:
          routeShape.forEach(function (point) {
            let parts = point.split(',');
            linestring.pushLatLngAlt(parts[0], parts[1]);
          });

          // Retrieve the mapped positions of the requested waypoints:
          startPoint = route.waypoint[0].mappedPosition;
          endPoint = route.waypoint[1].mappedPosition;

          // Create a marker for the start point:
          let startMarker = new H.map.Marker({
            lat: startPoint.latitude,
            lng: startPoint.longitude,
          });

          // Create a marker for the end point:
          let endMarker = new H.map.Marker({
            lat: endPoint.latitude,
            lng: endPoint.longitude,
          });

          var routeOutline = new H.map.Polyline(linestring, {
            style: {
              lineWidth: 10,
              strokeColor: 'rgba(0, 128, 255, 0.7)',
              lineTailCap: 'arrow-tail',
              lineHeadCap: 'arrow-head',
            },
          });
          // Create a patterned polyline:
          var routeArrows = new H.map.Polyline(linestring, {
            style: {
              lineWidth: 10,
              fillColor: 'white',
              strokeColor: 'rgba(255, 255, 255, 1)',
              lineDash: [0, 2],
              lineTailCap: 'arrow-tail',
              lineHeadCap: 'arrow-head',
            },
          });
          // create a group that represents the route line and contains
          // outline and the pattern
          let routeLine = new H.map.Group(); // Set the map's viewport to make the whole route visible:

          routeLine.addObjects([routeOutline, routeArrows]);
          // Add the route polyline and the two markers to the map:
          this.map.addObjects([routeLine, startMarker, endMarker]);
          this.map
            .getViewModel()
            .setLookAtData({ bounds: routeLine.getBoundingBox() });
        }
      },
      function (error) {
        alert(error.message);
      }
    );
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

  public ngOnInit() {
    this.userId = this.route.snapshot.paramMap.get('userId');
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
    let ui = H.ui.UI.createDefault(this.map, defaultLayers);

    //add marker
    this.addMarker(this.map, this.location);

    //Initialise Route
    this.routeInit(this.location,this.destinationCoorinate);
  }
}
