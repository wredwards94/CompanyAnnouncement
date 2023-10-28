import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { UserService } from 'src/services/user.service';

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
  selector: 'app-user-modal',
  templateUrl: './user-modal.component.html',
  styleUrls: ['./user-modal.component.css']
})
export class UserModalComponent {
  
  @Input() showModal: boolean = false;
  @Output() close = new EventEmitter<void>();

  onClose() {
    this.close.emit();
  }

  register: FormGroup;
  
  selectedRole: string = "";
  adminRole: string = '';

  constructor(private formBuilder: FormBuilder, private userService : UserService) {
    this.register = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required],
      adminRole: ['', Validators.required]
    }, {
      validator: passwordMatchValidator
    });
  }

  onRoleChange(event: Event) {
    this.selectedRole = (event.target as HTMLSelectElement).value;
  }

  async onSubmit() {
    if (this.register.valid) {
      try {
      // Passwords match, proceed with form submission
      
      const credentials = {
        username: this.register.get('email')?.value,
        password: this.register.get('password')?.value
      }

      const profile = {
        firstName : this.register.get('firstName')?.value,
        lastName : this.register.get('lastName')?.value,
        email : this.register.get('email')?.value,
        phone: '000-000-0000'
      }

      const user = {
        credentials,
        profile,
        admin: this.register.get('adminRole')?.value
      }
      await this.userService.addUser(user)      
      const companyID = this.userService.getCompany()?.id
      await this.userService.addUserToCompany(companyID, this.register.get('email')?.value)

      alert('Form submitted successfully!');
    }
    catch (error: any) {
      alert('Error: ' + error.message)
    }
      this.onClose();
      
    }
  }
}

