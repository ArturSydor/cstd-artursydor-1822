import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {OrganisationRegistrationPayload} from "../OrganisationRegistrationPayload";
import {AuthService} from "../auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register-organisation',
  templateUrl: './register-organisation.component.html',
  styleUrls: ['./register-organisation.component.css']
})
export class RegisterOrganisationComponent implements OnInit {
  registerForm: FormGroup;
  registerPayload: OrganisationRegistrationPayload;

  constructor(private formBuilder: FormBuilder, private authService: AuthService,
              private router: Router) {
    this.registerForm = this.formBuilder.group({
      name:'',
      email:'',
      password:'',
      confirmPassword:''
    });
    this.registerPayload = {
      name:'',
      email:'',
      memberApprovalRequired: true,
      creator: {}
    };
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.registerPayload.name = this.registerForm.get('name').value;
    this.registerPayload.email = this.registerForm.get('email').value;
    this.registerPayload.memberApprovalRequired = this.registerForm.get('requiredApproval').value;
    this.registerPayload.creator = this.registerForm.get('confirmPassword').value;

    this.authService.register(this.registerPayload).subscribe(data => {
      console.log('register success');
      this.router.navigateByUrl('/register-success')
    }, error => {
      console.log('register fail');
    });
  }
}
