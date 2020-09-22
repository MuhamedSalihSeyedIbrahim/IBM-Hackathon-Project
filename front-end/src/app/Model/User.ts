import { Coordinate } from './Coordinate';

export interface User{
    userId:string
    contact:string
    firstname:string
    lastname:string
    age:number
    gender:string
    password:string
    userType:string
    status:string
    coordinates:Coordinate
}