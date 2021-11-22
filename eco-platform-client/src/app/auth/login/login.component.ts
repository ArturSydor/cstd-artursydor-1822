import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../auth.service";
import {LoginPayload} from "../login-payload";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loginPayload: LoginPayload;

  constructor(private formBuilder: FormBuilder, private authService: AuthService,
              private router: Router) {
    this.loginForm = this.formBuilder.group({
      userEmail: new FormControl('', [Validators.required, Validators.email]),
      userPassword: new FormControl('', Validators.required)
    });
    this.loginPayload = {
      email: '',
      password: '',
    };
  }

  ngOnInit(): void {
  }


  onSubmit() {
    this.loginPayload.email = this.loginForm.get('userEmail').value;
    this.loginPayload.password = this.loginForm.get('userPassword').value;

    this.authService.login(this.loginPayload).subscribe(data => {
      if(data) {
        console.log('login success');
        this.router.navigateByUrl('/home');
      } else {
        console.log('login fail');
      }
    });
  }
}
