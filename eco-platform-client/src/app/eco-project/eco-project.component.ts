import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Actions} from "../shared/actions";
import {EcoProjectService} from "../eco-project.service";
import {Router} from "@angular/router";
import {DialogHelperService} from "../shared/dialog-helper.service";
import {EcoProjectPayload} from "../model/eco-project-payload";
import {EcoProjectTransferService} from "./eco-project-transfer.service";
import {DialogInterface} from "../interfaces/dialog.interface";
import {DialogComponent} from "../components/dialog/dialog.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-eco-project',
  templateUrl: './eco-project.component.html',
  styleUrls: ['./eco-project.component.css']
})
export class EcoProjectComponent implements OnInit {

  ecoProjectForm: FormGroup;
  ecoProjectPayload: EcoProjectPayload;

  constructor(private projectService: EcoProjectService, private router: Router, private dialogHelper: DialogHelperService,
              private projectDataTransferService: EcoProjectTransferService, private dialog: MatDialog) {
    this.ecoProjectForm = new FormGroup({
      projectName: new FormControl('', Validators.required),
      maxPointsPerUser: new FormControl(1, [Validators.required, Validators.min(1)]),
      projectDescription: new FormControl('', Validators.required)
    });
    this.ecoProjectPayload = new EcoProjectPayload();
    this.ecoProjectPayload.name = '';
    this.ecoProjectPayload.maxAllowedPointsPerUser = 1;
    this.ecoProjectPayload.description = '';
    this.ecoProjectPayload.published = false;
    this.ecoProjectPayload.closed = false;
  }

  ngOnInit(): void {
    if (this.projectDataTransferService.getData()) {
      this.ecoProjectPayload = this.projectDataTransferService.getData();
      this.ecoProjectForm = new FormGroup({
        projectName: new FormControl(this.ecoProjectPayload.name, Validators.required),
        maxPointsPerUser: new FormControl(this.ecoProjectPayload.maxAllowedPointsPerUser, [Validators.required, Validators.min(1)]),
        projectDescription: new FormControl(this.ecoProjectPayload.description, Validators.required)
      });
      this.projectDataTransferService.setData(null);
    }
  }

  onSubmit(buttonType: string) {
    if (Actions.CANCEL === buttonType) {
      this.askForConfirmation(Actions.CANCEL, () => this.performCancel());
      return;
    }

    this.ecoProjectPayload.name = this.ecoProjectForm.get('projectName').value;
    this.ecoProjectPayload.maxAllowedPointsPerUser = this.ecoProjectForm.get('maxPointsPerUser').value;
    this.ecoProjectPayload.description = this.ecoProjectForm.get('projectDescription').value;

    if (Actions.PUBLISH === buttonType) {
      this.ecoProjectPayload.published = true;
      this.askForConfirmation(Actions.PUBLISH, () => this.performSaving())
    } else {
      this.askForConfirmation(Actions.SAVE, () => this.performSaving())
    }
  }

  askForConfirmation(action: Actions, func: () => void) {
    const dialogInterface: DialogInterface = {
      dialogHeader: 'Info',
      dialogContent: 'Do you want to ' + action + ' this project?',
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
    this.router.navigateByUrl('/home');
  }

  performSaving() {
    this.projectService.saveNewProject(this.ecoProjectPayload).subscribe(data => {
      console.log('Project successfully saved');
      this.router.navigateByUrl('/home');
    }, error => {
      console.error(error)
      this.dialogHelper.showError(error.error);
    })
  }
}
