import { Component, OnInit } from '@angular/core';
import {AirPollutionSensorService} from "../air-pollution-sensor.service";
import {DialogHelperService} from "../../shared/dialog-helper.service";
import {AirPollutionQualityCalculationReportPayload} from "../air-pollution-quality-calculation-report-payload";

@Component({
  selector: 'app-air-pollution-report',
  templateUrl: './air-pollution-report.component.html',
  styleUrls: ['./air-pollution-report.component.css']
})
export class AirPollutionReportComponent implements OnInit {

  reportData: AirPollutionQualityCalculationReportPayload;

  constructor(private airPollutionSensorService: AirPollutionSensorService,
              private dialogHelper: DialogHelperService) { }

  ngOnInit(): void {
    this.airPollutionSensorService.getReport().subscribe(data => {
      this.reportData = data;
    }, error => {
      console.error(error);
      this.dialogHelper.showError(error.error);
    })
  }

  isReportDone(): boolean {
    return !!this.reportData;
  }

}
