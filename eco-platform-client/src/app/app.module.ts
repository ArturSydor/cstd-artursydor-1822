import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './header/header.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {LoginComponent} from './auth/login/login.component';
import {RegisterUserComponent} from './auth/register-user/register-user.component';
import {RegisterOrganisationComponent} from './auth/register-organisation/register-organisation.component';
import {RegisterSuccessComponent} from './auth/register-success/register-success.component';
import {HomeComponent} from './home/home.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {NgxWebstorageModule} from "ngx-webstorage";
import {HttpClientInterceptor} from "./http-client-interceptor";
import {DialogComponent} from './components/dialog/dialog.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {MatButtonModule} from "@angular/material/button";
import {MatDialogModule} from "@angular/material/dialog";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {JoinRequestsComponent} from './user/join-requests/join-requests.component';
import {EcoProjectComponent} from './eco-project/eco-project.component';
import {MatCardModule} from "@angular/material/card";
import {FlexLayoutModule} from "@angular/flex-layout";
import {EditorModule} from "@tinymce/tinymce-angular";
import { UserProfileComponent } from './user/user-profile/user-profile.component';
import {DatePipe} from "@angular/common";
import {MatMenuModule} from "@angular/material/menu";
import { ChangePasswordComponent } from './auth/change-password/change-password.component';
import { AirPollutionSensorComponent } from './sensor/air-pollution-sensor/air-pollution-sensor.component';
import { AirPollutionSensorEditComponent } from './sensor/air-pollution-sensor-edit/air-pollution-sensor-edit.component';
import { AirPollutionReportComponent } from './sensor/air-pollution-report/air-pollution-report.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    RegisterUserComponent,
    RegisterOrganisationComponent,
    RegisterSuccessComponent,
    HomeComponent,
    DialogComponent,
    JoinRequestsComponent,
    EcoProjectComponent,
    UserProfileComponent,
    ChangePasswordComponent,
    AirPollutionSensorComponent,
    AirPollutionSensorEditComponent,
    AirPollutionReportComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatDialogModule,
    MatButtonModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatCardModule,
    FlexLayoutModule,
    EditorModule,
    NgxWebstorageModule.forRoot(),
    MatMenuModule
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: HttpClientInterceptor, multi: true}, DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule {
}
