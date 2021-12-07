import {Injectable} from '@angular/core';
import {AirPollutionSensorPayload} from "./air-pollution-sensor-payload";

@Injectable({
  providedIn: 'root'
})
export class AirPollutionSensorTransferService {

  airPollutionSensorData: AirPollutionSensorPayload;
  new = true;

  constructor() {
  }

  getData(): AirPollutionSensorPayload {
    return this.airPollutionSensorData;
  }

  setDataForUpdate(sensorPayload: AirPollutionSensorPayload) {
    this.airPollutionSensorData = sensorPayload;
    this.new = false;
  }

  isNew(): boolean {
    return this.new;
  }

  clear() {
    this.airPollutionSensorData = null;
    this.new = true;
  }
}
