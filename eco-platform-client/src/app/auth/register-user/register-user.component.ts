import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../auth.service";
import {Router} from "@angular/router";
import {RolePayload} from "../../model/role-payload";
import {OrganisationService} from "../../organisation.service";
import {SimpleOrganisationPayload} from "../../model/simple-organisation-payload";
import {matchValidator} from "../confirmed-validator";
import {UserRegistrationPayload} from "../user-registration-payload";
import {Roles} from "../roles";

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})
export class RegisterUserComponent implements OnInit {

  registerForm: FormGroup;
  userRegisterPayload: UserRegistrationPayload;
  defaultRole: RolePayload;
  availableOrganisations: Array<SimpleOrganisationPayload>;


  constructor(private formBuilder: FormBuilder, private authService: AuthService,
              private organisationService: OrganisationService, private router: Router) {
    this.registerForm = this.formBuilder.group({
      requiredApproval: true,
      userFirstName: new FormControl('', Validators.required),
      userLastName: new FormControl('', Validators.required),
      userEmail: new FormControl('', [Validators.required, Validators.email]),
      userPassword: new FormControl('', Validators.required),
      userConfirmPassword: new FormControl('', [Validators.required, matchValidator('userPassword')]),
      organisation: new FormControl(this.availableOrganisations, Validators.required)
    });
    this.userRegisterPayload = {
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      confirmPassword: '',
      role: null,
      organisation: null
    };
  }

  ngOnInit(): void {
    this.organisationService.getAll().subscribe((data: Array<SimpleOrganisationPayload>) => {
      this.availableOrganisations = data;
    }, error => {
      console.error('Failed to get roles: ' + error);
    });

    this.authService.getAllRoles().subscribe((data: Array<RolePayload>) => {
      this.defaultRole = data.find(role => role.name == Roles.INHABITANT);
    }, error => {
      console.error('Failed to get roles: ' + error);
    });
  }

  onSubmit() {
    this.userRegisterPayload.firstName = this.registerForm.get('userFirstName').value;
    this.userRegisterPayload.lastName = this.registerForm.get('userLastName').value;
    this.userRegisterPayload.email = this.registerForm.get('userEmail').value;
    this.userRegisterPayload.password = this.registerForm.get('userPassword').value;
    this.userRegisterPayload.confirmPassword = this.registerForm.get('userConfirmPassword').value;
    this.userRegisterPayload.role = this.defaultRole;

    this.authService.registerUser(this.userRegisterPayload).subscribe(data => {
      console.log('register success');
      this.router.navigateByUrl('/register-success')
    }, error => {
      console.log('register fail');
    });
  }

  onInputChange() {
    this.registerForm.get('userConfirmPassword').updateValueAndValidity();
  }
}
