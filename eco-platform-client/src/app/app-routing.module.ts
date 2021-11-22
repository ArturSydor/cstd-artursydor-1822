import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RegisterOrganisationComponent} from "./auth/register-organisation/register-organisation.component";
import {LoginComponent} from "./auth/login/login.component";
import {RegisterUserComponent} from "./auth/register-user/register-user.component";
import {RegisterSuccessComponent} from "./auth/register-success/register-success.component";
import {HomeComponent} from "./home/home.component";
import {AuthGuard} from "./auth.guard";

const routes: Routes = [
  {path: 'register-organisation', component: RegisterOrganisationComponent},
  {path: 'register-user', component: RegisterUserComponent},
  {path: 'register-success', component: RegisterSuccessComponent},
  {path: 'login', component: LoginComponent},
  {path: 'home', component: HomeComponent, canActivate: [AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
