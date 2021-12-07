import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RegisterOrganisationComponent} from "./auth/register-organisation/register-organisation.component";
import {LoginComponent} from "./auth/login/login.component";
import {RegisterUserComponent} from "./auth/register-user/register-user.component";
import {RegisterSuccessComponent} from "./auth/register-success/register-success.component";
import {HomeComponent} from "./home/home.component";
import {AuthGuard} from "./auth.guard";
import {JoinRequestsComponent} from "./user/join-requests/join-requests.component";
import {EcoProjectComponent} from "./eco-project/eco-project.component";
import {UserProfileComponent} from "./user/user-profile/user-profile.component";
import {ChangePasswordComponent} from "./auth/change-password/change-password.component";
import {AirPollutionSensorComponent} from "./sensor/air-pollution-sensor/air-pollution-sensor.component";
import {AirPollutionSensorEditComponent} from "./sensor/air-pollution-sensor-edit/air-pollution-sensor-edit.component";
import {AirPollutionReportComponent} from "./sensor/air-pollution-report/air-pollution-report.component";

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  {path: 'register-organisation', component: RegisterOrganisationComponent},
  {path: 'register-user', component: RegisterUserComponent},
  {path: 'register-success', component: RegisterSuccessComponent},
  {path: 'login', component: LoginComponent},
  {path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'organisation-join-requests', component: JoinRequestsComponent, canActivate: [AuthGuard]},
  {path: 'eco-project', component: EcoProjectComponent, canActivate: [AuthGuard]},
  {path: 'user-profile', component: UserProfileComponent, canActivate: [AuthGuard]},
  {path: 'change-password', component: ChangePasswordComponent, canActivate: [AuthGuard]},
  {path: 'sensors/air-pollution', component: AirPollutionSensorComponent, canActivate: [AuthGuard]},
  {path: 'sensors/air-pollution-edit', component: AirPollutionSensorEditComponent, canActivate: [AuthGuard]},
  {path: 'sensors/air-pollution-report', component: AirPollutionReportComponent, canActivate: [AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
