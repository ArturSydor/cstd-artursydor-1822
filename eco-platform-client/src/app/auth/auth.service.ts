import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {LocalStorageService} from "ngx-webstorage";
import {RolePayload} from "../model/role-payload";
import {UserRegistrationPayload} from "./user-registration-payload";
import {LoginPayload} from "./login-payload";
import {JwtAuthResponse} from "./jwt-auth-response";
import {map} from "rxjs/operators";


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private url = 'http://localhost:8080/';

  constructor(private httpClient: HttpClient, private localStorageService: LocalStorageService) {
  }

  login(loginPayload: LoginPayload): Observable<boolean> {
    return this.httpClient.post<JwtAuthResponse>(this.url + 'auth/login', loginPayload).pipe(map(data => {
      this.localStorageService.store("authenticationToken", data.authenticationToken);
      this.localStorageService.store("email", data.email);
      this.localStorageService.store("role", data.role);
      return true;
    }));
  }

  isAuthenticated(): boolean {
    return this.localStorageService.retrieve("email") !== null;
  }

  getAllRoles(): Observable<Array<RolePayload>> {
    return this.httpClient.get<Array<RolePayload>>(this.url + 'roles');
  }

  logout() {
    this.localStorageService.clear("authenticationToken")
    this.localStorageService.clear("email");
    this.localStorageService.clear("role");
  }

  registerUser(registerPayload: UserRegistrationPayload): Observable<any> {
    return this.httpClient.post(this.url + 'auth/registration', registerPayload)
  }

}
