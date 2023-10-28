import { Component } from '@angular/core';
import { AbstractControl, FormGroup, FormBuilder, Validators } from '@angular/forms';


function passwordMatchValidator(control: AbstractControl) {
  const password = control.get('password')?.value;
  const confirmPassword = control.get('confirmPassword')?.value;

  if (password !== confirmPassword) {
    control.get('confirmPassword')?.setErrors({ passwordMatch: true });
    return { passwordMatch: true };
  } else {
    return null;
  }
}

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent {

  register: FormGroup;
  firstName: string = "hi";
  selectedRole: string = 'Pick a role';

  constructor(private formBuilder: FormBuilder) {
    this.register = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required],
      adminRole: [this.selectedRole, Validators.required]
    }, {
      validator: passwordMatchValidator
    });
  }

  onRoleChange(event: Event) {
    this.selectedRole = (event.target as HTMLSelectElement).value;
  }

  onSubmit() {
    if (this.register.valid) {
      const password = this.register.get('password')?.value;
      const confirmPassword = this.register.get('confirmPassword')?.value;
      console.log("hit")
      if (password === confirmPassword) {
        // Passwords match, proceed with form submission
        alert('Form submitted successfully!');
        //send a post request for the user. 
      } else {
        alert('Passwords do not match. Please try again.');
      }
    }
  }
}