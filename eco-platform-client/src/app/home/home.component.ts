import {Component, OnInit} from '@angular/core';
import {EcoProjectService} from "../eco-project.service";
import {Observable} from "rxjs";
import {EcoProjectPayload} from "../model/eco-project-payload";
import {MappingHelperService} from "../shared/mapping-helper.service";
import {AuthService} from "../auth/auth.service";
import {DialogHelperService} from "../shared/dialog-helper.service";
import {EcoProjectTransferService} from "../eco-project/eco-project-transfer.service";
import {Router} from "@angular/router";

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
              private projectDataTransferService: EcoProjectTransferService, private router: Router) {
  }

  ngOnInit(): void {
    this.onlyPersonalProjects = false;
    this.ecoProjects = this.ecoProjectsService.getAllProjects(this.onlyPersonalProjects);
  }

  reloadProjects(onlyPersonal: boolean) {
    this.ecoProjects = this.ecoProjectsService.getAllProjects(onlyPersonal);
  }

  publishProject(projectToPublish: EcoProjectPayload) {
    projectToPublish.published = true;
    this.ecoProjectsService.updateProject(projectToPublish).subscribe(data => {
      console.log('Project ' + projectToPublish.name + ' was updated');
      this.reloadProjects(this.onlyPersonalProjects);
    }, error => {
      console.error(error);
      this.dialogHelper.showError(error.error);
    })
  }

  onlyForCreators(creatorEmail: String): boolean {
    return this.authService.getCurrentLogin() == creatorEmail;
  }

  editProject(projectData: EcoProjectPayload) {
    this.projectDataTransferService.setData(projectData);
    this.router.navigateByUrl('/eco-project');
  }

  closeProject(project: EcoProjectPayload) {
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

  removeProject(id: BigInteger) {
    this.ecoProjectsService.removeProject(id).subscribe(data => {
      console.log('Project with id=' + id + ' was closed');
      this.reloadProjects(this.onlyPersonalProjects);
    }, error => {
      console.error(error);
      this.dialogHelper.showError(error.error);
    })
  }

  supportProject(id: BigInteger) {
    this.ecoProjectsService.updatePoints(id, 1).subscribe(data => {
      console.log('Points were added to project with id=' + id);
      this.reloadProjects(this.onlyPersonalProjects);
    }, error => {
      console.error(error);
      this.dialogHelper.showError(error.error);
    })
  }
}
