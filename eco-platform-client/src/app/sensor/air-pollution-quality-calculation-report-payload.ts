import {AirPollutionSensorPayload} from "./air-pollution-sensor-payload";
import {AirPollutionDataPayload} from "./air-pollution-data-payload";

export class AirPollutionQualityCalculationReportPayload {
  sensor: AirPollutionSensorPayload;
  sensorData: AirPollutionDataPayload;
  norma: AirPollutionDataPayload;
}
