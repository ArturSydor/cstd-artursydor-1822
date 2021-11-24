import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserPayload} from "./user-payload";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = 'http://localhost:8080/users';

  constructor(private httpClient: HttpClient) {
  }

  getAll(organisationId: Number): Observable<Array<UserPayload>> {
    return this.httpClient.get<Array<UserPayload>>(this.url + '/approvals?organisationId=' + organisationId);
  }

  approveUser(userId: Number): Observable<any> {
    return this.httpClient.post(this.url + '/approvals/' + userId, null);
  }

}
