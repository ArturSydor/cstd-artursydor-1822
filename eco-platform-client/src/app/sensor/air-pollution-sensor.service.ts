import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AirPollutionSensorPayload} from "./air-pollution-sensor-payload";

@Injectable({
  providedIn: 'root'
})
export class AirPollutionSensorService {

  private url = 'http://localhost:8080/air-pollution-sensor';

  constructor(private httpClient: HttpClient) { }

  getAllSensors(): Observable<Array<AirPollutionSensorPayload>> {
    return this.httpClient.get<Array<AirPollutionSensorPayload>>(this.url);
  }

  getOne(id: BigInteger): Observable<AirPollutionSensorPayload> {
    return this.httpClient.get<AirPollutionSensorPayload>(this.url + '/' + id);
  }

  create(newSensor: AirPollutionSensorPayload): Observable<AirPollutionSensorPayload> {
    return this.httpClient.post<AirPollutionSensorPayload>(this.url, newSensor);
  }

  update(sensor: AirPollutionSensorPayload): Observable<AirPollutionSensorPayload> {
    return this.httpClient.put<AirPollutionSensorPayload>(this.url, sensor);
  }

  deleteOne(id: BigInteger): Observable<any> {
    return this.httpClient.delete(this.url + '/' + id);
  }

}
