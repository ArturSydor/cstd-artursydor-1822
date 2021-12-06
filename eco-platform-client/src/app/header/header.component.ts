import {Component, OnInit} from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {Router} from "@angular/router";
import {DialogInterface} from "../interfaces/dialog.interface";
import {DialogComponent} from "../components/dialog/dialog.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router, private dialog: MatDialog) {
  }

  ngOnInit(): void {
  }

  isVisible(): boolean {
    return this.authService.isAuthenticated();
  }

  visibleForManagers(): boolean {
    return this.authService.isAuthenticatedManager();
  }

  logout() {
    this.askForConfirmation();
  }

  askForConfirmation() {
    const dialogInterface: DialogInterface = {
      dialogHeader: 'Info',
      dialogContent: 'Do you want to logout?',
      cancelButtonLabel: 'Cancel',
      confirmButtonLabel: 'Submit',
      callbackMethod: () => {
        this.performLogout();
      }
    };
    this.dialog.open(DialogComponent, {
      width: '500px',
      data: dialogInterface,
    });
  }

  performLogout() {
    this.authService.logout();
    this.router.navigateByUrl('/login');
  }

}
