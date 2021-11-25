import { Injectable } from '@angular/core';
import {EcoProjectPayload} from "../model/eco-project-payload";

@Injectable({
  providedIn: 'root'
})
export class EcoProjectTransferService {

  ecoProjectData: EcoProjectPayload

  constructor() { }

  getData(): EcoProjectPayload {
    return this.ecoProjectData;
  }

  setData(projectData: EcoProjectPayload) {
    this.ecoProjectData = projectData;
  }

}
