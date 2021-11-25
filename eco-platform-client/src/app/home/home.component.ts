import {Component, OnInit} from '@angular/core';
import {EcoProjectService} from "../eco-project.service";
import {Observable} from "rxjs";
import {EcoProjectPayload} from "../model/eco-project-payload";
import {MappingHelperService} from "../shared/mapping-helper.service";
import {AuthService} from "../auth/auth.service";
import {DialogHelperService} from "../shared/dialog-helper.service";
import {EcoProjectTransferService} from "../eco-project/eco-project-transfer.service";
import {Router} from "@angular/router";
import {DialogInterface} from "../interfaces/dialog.interface";
import {DialogComponent} from "../components/dialog/dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {Actions} from "../shared/actions";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  onlyPersonalProjects: boolean;
  ecoProjects: Observable<Array<EcoProjectPayload>>;

  constructor(private ecoProjectsService: EcoProjectService, private authService: AuthService,
              public mappingHelper: MappingHelperService, private dialogHelper: DialogHelperService,
              private projectDataTransferService: EcoProjectTransferService, private router: Router,
              private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.onlyPersonalProjects = false;
    this.ecoProjects = this.ecoProjectsService.getAllProjects(this.onlyPersonalProjects);
  }

  reloadProjects(onlyPersonal: boolean) {
    this.ecoProjects = this.ecoProjectsService.getAllProjects(onlyPersonal);
  }

  publishProject(projectToPublish: EcoProjectPayload) {
    this.askForConfirmation(Actions.PUBLISH, () => this.performPublishProject(projectToPublish))
  }

  onlyForCreators(creatorEmail: String): boolean {
    return this.authService.getCurrentLogin() == creatorEmail;
  }

  editProject(projectData: EcoProjectPayload) {
    this.projectDataTransferService.setData(projectData);
    this.router.navigateByUrl('/eco-project');
  }

  closeProject(project: EcoProjectPayload) {
    this.askForConfirmation(Actions.CLOSE, () => this.performCloseProject(project));
  }

  removeProject(id: BigInteger) {
    this.askForConfirmation(Actions.DELETE, () => this.performRemoveProject(id));
  }

  supportProject(id: BigInteger) {
    this.askForConfirmation(Actions.SUPPORT, () => this.performSupportProject(id));
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

  performPublishProject(projectToPublish: EcoProjectPayload) {
    projectToPublish.published = true;
    this.ecoProjectsService.updateProject(projectToPublish).subscribe(data => {
      console.log('Project ' + projectToPublish.name + ' was updated');
      this.reloadProjects(this.onlyPersonalProjects);
    }, error => {
      console.error(error);
      projectToPublish.published = false;
      this.dialogHelper.showError(error.error);
    })
  }

  performCloseProject(project: EcoProjectPayload) {
    project.closed = true;
    this.ecoProjectsService.updateProject(project).subscribe(data => {
      console.log('Project ' + project.name + ' was closed');
      this.reloadProjects(this.onlyPersonalProjects);
    }, error => {
      project.closed = false;
      console.error(error);
      this.dialogHelper.showError(error.error);
    })
  }

  performRemoveProject(id: BigInteger) {
    this.ecoProjectsService.removeProject(id).subscribe(data => {
      console.log('Project with id=' + id + ' was closed');
      this.reloadProjects(this.onlyPersonalProjects);
    }, error => {
      console.error(error);
      this.dialogHelper.showError(error.error);
    })
  }

  performSupportProject(id: BigInteger) {
    this.ecoProjectsService.updatePoints(id, 1).subscribe(data => {
      console.log('Points were added to project with id=' + id);
      this.reloadProjects(this.onlyPersonalProjects);
    }, error => {
      console.error(error);
      this.dialogHelper.showError(error.error);
    })
  }

}
