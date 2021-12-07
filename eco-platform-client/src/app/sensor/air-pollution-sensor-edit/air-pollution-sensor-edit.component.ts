import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AirPollutionSensorTransferService} from "../air-pollution-sensor-transfer.service";
import {DialogHelperService} from "../../shared/dialog-helper.service";
import {Router} from "@angular/router";
import {AirPollutionSensorPayload} from "../air-pollution-sensor-payload";
import {Actions} from "../../shared/actions";
import {DialogInterface} from "../../interfaces/dialog.interface";
import {DialogComponent} from "../../components/dialog/dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {AirPollutionSensorService} from "../air-pollution-sensor.service";

@Component({
  selector: 'app-air-pollution-sensor-edit',
  templateUrl: './air-pollution-sensor-edit.component.html',
  styleUrls: ['./air-pollution-sensor-edit.component.css']
})
export class AirPollutionSensorEditComponent implements OnInit {

  sensorForm: FormGroup;
  sensorData: AirPollutionSensorPayload;

  constructor(private transferService: AirPollutionSensorTransferService, private dialogHelper: DialogHelperService,
              private router: Router, private dialog: MatDialog, private airPollutionSensorService: AirPollutionSensorService) {
  }

  ngOnInit(): void {
    if (this.transferService.isNew()) {
      this.setUpNew();
    } else {
      this.setUpEdit();
    }
  }

  onSubmit(buttonType: string) {
    if (Actions.CANCEL === buttonType) {
      this.askForConfirmation(Actions.CANCEL, () => this.performCancel());
      return;
    }
    this.askForConfirmation(Actions.SAVE, () => this.performSaving())
  }

  setUpNew() {
    this.sensorForm = new FormGroup({
      externalIdentifier: new FormControl('', Validators.required),
      latitude: new FormControl('', Validators.required),
      longitude: new FormControl('', Validators.required)
    });
    this.sensorData = {
      id: null,
      externalIdentifier: '',
      longitude: '',
      latitude: '',
      organisation: null,
      creator: null
    }
  }

  setUpEdit() {
    this.sensorData = this.transferService.getData();
    this.sensorForm = new FormGroup({
      externalIdentifier: new FormControl(this.sensorData.externalIdentifier, Validators.required),
      latitude: new FormControl(this.sensorData.latitude, Validators.required),
      longitude: new FormControl(this.sensorData.longitude, Validators.required)
    });
    this.transferService.clear();
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

  performCancel() {
    this.router.navigateByUrl('/sensors/air-pollution');
  }

  performSaving() {
    this.sensorData.externalIdentifier = this.sensorForm.get('externalIdentifier').value;
    this.sensorData.longitude = this.sensorForm.get('longitude').value;
    this.sensorData.latitude = this.sensorForm.get('latitude').value;

    this.airPollutionSensorService.create(this.sensorData).subscribe(data => {
      console.log('Project successfully saved');
      this.router.navigateByUrl('/sensors/air-pollution');
    }, error => {
      console.error(error)
      this.dialogHelper.showError(error.error);
    })
  }

}
