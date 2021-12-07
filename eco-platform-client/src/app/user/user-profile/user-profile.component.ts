import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../user.service";
import {Router} from "@angular/router";
import {DialogHelperService} from "../../shared/dialog-helper.service";
import {UserPayload} from "../user-payload";
import {AuthService} from "../../auth/auth.service";
import {DatePipe} from "@angular/common";
import {Actions} from "../../shared/actions";
import {DialogInterface} from "../../interfaces/dialog.interface";
import {DialogComponent} from "../../components/dialog/dialog.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  profileForm: FormGroup;
  userData: UserPayload;
  isEmailOrActiveChanged: boolean;

  constructor(private formBuilder: FormBuilder, private userService: UserService,
              private dialogHelper: DialogHelperService, private router: Router,
              private authService: AuthService, private datePipe: DatePipe,
              private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.isEmailOrActiveChanged = false;
    let email = this.authService.getCurrentLogin();
    this.userService.getOneByEmail(email).subscribe(data => {
      this.userData = data;
      this.profileForm = this.formBuilder.group({
        userFirstName: new FormControl(data.firstName, Validators.required),
        userLastName: new FormControl(data.lastName, Validators.required),
        userEmail: new FormControl(data.email, [Validators.required, Validators.email]),
        activeUser: data.active,
        joined: new FormControl(this.datePipe.transform(data.joined, 'yyyy-MM-dd'), Validators.required),
      })
    }, error => {
      console.log(error);
      this.dialogHelper.showError('Cannot get profile data for ' + email);
    })
  }

  onSubmit(action: string) {
    if (Actions.CANCEL === action) {
      this.askForConfirmation(Actions.CANCEL, () => this.performCancel());
      return;
    }

    this.userData.firstName = this.profileForm.get('userFirstName').value;
    this.userData.lastName = this.profileForm.get('userLastName').value;
    let newEmail = this.profileForm.get('userEmail').value;
    let active = this.profileForm.get('activeUser').value;
    if (this.userData.email !== newEmail || this.userData.active !== active) {
      this.isEmailOrActiveChanged = true;
    }
    this.userData.email = newEmail;
    this.userData.active = active;

    this.askForConfirmation(Actions.SAVE, () => this.performSaving())
  }

  askForConfirmation(action: Actions, func: () => void) {
    const dialogInterface: DialogInterface = {
      dialogHeader: 'Info',
      dialogContent: 'Do you want to ' + action + ' this project?',
      cancelButtonLabel: 'Cancel',
      confirmButtonLabel: 'Submit',
      callbackMethod: () => {
        func();
      }
    };
    this.dialog.open(DialogComponent, {
      width: '500px',
      data: dialogInterface,
    });
  }

  performCancel() {
    this.router.navigateByUrl('/home');
  }

  performSaving() {
    this.userService.updateUser(this.userData).subscribe(data => {
      console.log('User successfully updated');
      if (this.isEmailOrActiveChanged) {
        this.authService.logout();
        this.router.navigateByUrl('/login');
        return;
      }
      this.router.navigateByUrl('/home');
    }, error => {
      console.error(error)
      this.dialogHelper.showError(error.error);
    })
  }

}
