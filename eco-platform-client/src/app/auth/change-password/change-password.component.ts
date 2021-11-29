import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ChangePasswordPayload} from "../change-password-payload";
import {matchValidator} from "../confirmed-validator";
import {Actions} from "../../shared/actions";
import {DialogInterface} from "../../interfaces/dialog.interface";
import {DialogComponent} from "../../components/dialog/dialog.component";
import {Router} from "@angular/router";
import {DialogHelperService} from "../../shared/dialog-helper.service";
import {MatDialog} from "@angular/material/dialog";
import {AuthService} from "../auth.service";

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  changePasswordForm: FormGroup;
  changePasswordData: ChangePasswordPayload;

  constructor(private formBuilder: FormBuilder, private router: Router,
              private dialogHelper: DialogHelperService, private dialog: MatDialog,
              private authService: AuthService) {
    this.changePasswordForm = this.formBuilder.group({
      newUserPassword: new FormControl('', Validators.required),
      userConfirmPassword: new FormControl('', [Validators.required, matchValidator('newUserPassword')]),
    });
    this.changePasswordData = {
      email: '',
      newPassword: ''
    };
  }

  ngOnInit(): void {
  }

  onSubmit(action: string) {
    if (Actions.CANCEL === action) {
      this.askForConfirmation(Actions.CANCEL, () => this.performCancel());
      return;
    }

    this.changePasswordData.email = this.authService.getCurrentLogin();
    this.changePasswordData.newPassword = this.changePasswordForm.get('newUserPassword').value;

    this.askForConfirmation(Actions.SAVE, () => this.performSaving())
  }

  onInputChange() {
    this.changePasswordForm.get('userConfirmPassword').updateValueAndValidity();
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
    this.authService.changePassword(this.changePasswordData).subscribe(data => {
      console.log('Password successfully updated');
      this.authService.logout();
      this.router.navigateByUrl('/login');
    }, error => {
      console.error(error)
      this.dialogHelper.showError(error.error);
    })
  }

}
