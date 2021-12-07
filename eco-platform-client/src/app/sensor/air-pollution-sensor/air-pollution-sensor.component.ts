import {Component, OnInit} from '@angular/core';
import {AirPollutionSensorService} from "../air-pollution-sensor.service";
import {AirPollutionSensorPayload} from "../air-pollution-sensor-payload";
import {Observable} from "rxjs";
import {AuthService} from "../../auth/auth.service";
import {AirPollutionSensorTransferService} from "../air-pollution-sensor-transfer.service";
import {Router} from "@angular/router";
import {DialogHelperService} from "../../shared/dialog-helper.service";
import {Actions} from "../../shared/actions";
import {DialogInterface} from "../../interfaces/dialog.interface";
import {DialogComponent} from "../../components/dialog/dialog.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-air-pollution-sensor',
  templateUrl: './air-pollution-sensor.component.html',
  styleUrls: ['./air-pollution-sensor.component.css']
})
export class AirPollutionSensorComponent implements OnInit {

  sensors: Observable<Array<AirPollutionSensorPayload>>;

  constructor(private airPollutionSensorService: AirPollutionSensorService, private authService: AuthService,
              private airPollutionSensorTransferService: AirPollutionSensorTransferService, private router: Router,
              private dialogHelper: DialogHelperService, private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.sensors = this.airPollutionSensorService.getAllSensors();
  }

  editSensor(sensor: AirPollutionSensorPayload) {
    this.airPollutionSensorTransferService.setDataForUpdate(sensor);
    this.router.navigateByUrl('sensors/air-pollution-edit');
  }


  removeSensor(id: BigInteger) {
    this.askForConfirmation(Actions.DELETE, () => this.performRemoving(id))
  }

  performRemoving(id: BigInteger) {
    this.airPollutionSensorService.deleteOne(id).subscribe(data => {
      console.log("Sensor with id " + id + " was removed");
      this.sensors = this.airPollutionSensorService.getAllSensors();
    }, error => {
      console.log(error);
      this.dialogHelper.showError(error.error);
    })
  }

  onlyForCreators(creatorEmail: String): boolean {
    return this.authService.getCurrentLogin() == creatorEmail;
  }

  askForConfirmation(action: Actions, func: () => void) {
    const dialogInterface: DialogInterface = {
      dialogHeader: 'Info',
      dialogContent: 'Do you want to ' + action + ' this sensor?',
      cancelButtonLabel: 'Cancel',
      confirmButtonLabel: 'Submit',
      callbackMethod: () => {
        func();
      }
    };
    this.dialog.open(DialogComponent, {
      width: '500px',
      data: dialogInterface,
    });
  }

}
