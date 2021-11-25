import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {EcoProjectPayload} from "./model/eco-project-payload";

@Injectable({
  providedIn: 'root'
})
export class EcoProjectService {

  private url = 'http://localhost:8080/eco-projects';

  constructor(private httpClient: HttpClient) {
  }

  getAllProjects(personaOnly: boolean): Observable<Array<EcoProjectPayload>> {
    return this.httpClient.get<Array<EcoProjectPayload>>(this.url + '?personalOnly=' + personaOnly);
  }

  saveNewProject(newProject: EcoProjectPayload): Observable<any> {
    return this.httpClient.post(this.url, newProject);
  }

  removeProject(id: BigInteger): Observable<any> {
    return this.httpClient.delete(this.url + '/' + id);
  }

  updateProject(project: EcoProjectPayload): Observable<EcoProjectPayload> {
    return this.httpClient.put<EcoProjectPayload>(this.url, project);
  }

  updatePoints(projectId: BigInteger, points: Number): Observable<any> {
    return this.httpClient.post(this.url + '/' + projectId + '/points?points=' + points, null);
  }

}
