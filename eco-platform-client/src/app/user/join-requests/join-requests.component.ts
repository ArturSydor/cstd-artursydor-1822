import { Component, OnInit } from '@angular/core';
import {Observable} from "rxjs";
import {UserPayload} from "../user-payload";
import {UserService} from "../user.service";
import {AuthService} from "../../auth/auth.service";
import {error} from "protractor";
import {DialogHelperService} from "../../shared/dialog-helper.service";

@Component({
  selector: 'app-join-requests',
  templateUrl: './join-requests.component.html',
  styleUrls: ['./join-requests.component.css']
})
export class JoinRequestsComponent implements OnInit {

  usersForApproval: Observable<Array<UserPayload>>

  constructor(private userService: UserService, private authService: AuthService,
              private dialogHelper: DialogHelperService) { }

  ngOnInit(): void {
    this.usersForApproval = this.userService.getAll(this.authService.getCurrentOrganisation());
  }

  approveUser(userId: Number) {
    this.userService.approveUser(userId).subscribe(data => {
      location.reload();
    }, error => {
      this.dialogHelper.showError(error.error);
    })
  }
}
