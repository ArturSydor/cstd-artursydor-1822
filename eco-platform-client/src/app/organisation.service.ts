import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {OrganisationRegistrationPayload} from "./auth/organisation-registration-payload";
import {Observable} from "rxjs";
import {SimpleOrganisationPayload} from "./model/simple-organisation-payload";
import {error} from "protractor";

@Injectable({
  providedIn: 'root'
})
export class OrganisationService {
  private url = 'http://localhost:8080/organisations';

  constructor(private httpClient: HttpClient) {
  }

  registerOrganisation(registerPayload: OrganisationRegistrationPayload): Observable<any> {
    return this.httpClient.post(this.url, registerPayload)
  }

  getAll(): Observable<Array<SimpleOrganisationPayload>> {
    return this.httpClient.get<Array<SimpleOrganisationPayload>>(this.url);
  }

}
