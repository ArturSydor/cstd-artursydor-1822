import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {OrganisationRegistrationPayload} from "../OrganisationRegistrationPayload";
import {AuthService} from "../auth.service";
import {Router} from "@angular/router";
import {UserRegistrationPayload} from "../UserRegistrationPayload";
import {RolePayload} from "../../model/RolePayload";
import {matchValidator} from "../ConfirmedValidator";
import {OrganisationService} from "../../organisation.service";

@Component({
  selector: 'app-register-organisation',
  templateUrl: './register-organisation.component.html',
  styleUrls: ['./register-organisation.component.css']
})
export class RegisterOrganisationComponent implements OnInit {
  registerForm: FormGroup;
  organisationRegisterPayload: OrganisationRegistrationPayload;
  userRegisterPayload: UserRegistrationPayload;
  creatorRole: RolePayload;

  constructor(private formBuilder: FormBuilder, private authService: AuthService,
              private organisationService: OrganisationService, private router: Router) {
    this.registerForm = this.formBuilder.group({
      organisationName: new FormControl('', Validators.required),
      organisationEmail: new FormControl('', [Validators.required, Validators.email]),
      requiredApproval: true,
      userFirstName: new FormControl('', Validators.required),
      userLastName: new FormControl('', Validators.required),
      userEmail: new FormControl('', [Validators.required, Validators.email]),
      userPassword: new FormControl('', Validators.required),
      userConfirmPassword: new FormControl('', [Validators.required, matchValidator('userPassword')])
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
    this.organisationRegisterPayload = {
      name: '',
      email: '',
      memberApprovalRequired: true,
      creator: this.userRegisterPayload
    };
  }

  ngOnInit(): void {
    this.authService.getAllRoles().subscribe((data: Array<RolePayload>) => {
      this.creatorRole = data.find(role => role.name == 'MANAGER');
    }, error => {
      console.error('Failed to get roles: ' + error);
    })
  }

  onSubmit() {
    this.organisationRegisterPayload.name = this.registerForm.get('organisationName').value;
    this.organisationRegisterPayload.email = this.registerForm.get('organisationEmail').value;
    this.organisationRegisterPayload.memberApprovalRequired = this.registerForm.get('requiredApproval').value;
    this.organisationRegisterPayload.creator.firstName = this.registerForm.get('userFirstName').value;
    this.organisationRegisterPayload.creator.lastName = this.registerForm.get('userLastName').value;
    this.organisationRegisterPayload.creator.email = this.registerForm.get('userEmail').value;
    this.organisationRegisterPayload.creator.password = this.registerForm.get('userPassword').value;
    this.organisationRegisterPayload.creator.confirmPassword = this.registerForm.get('userConfirmPassword').value;
    this.organisationRegisterPayload.creator.role = this.creatorRole;

    this.organisationService.registerOrganisation(this.organisationRegisterPayload).subscribe(data => {
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
