import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  email = '';
  password = '';
  error = '';

  constructor (
    private auth: AuthService,
    private router: Router
  ) {}

  onSubmit(): void {
    this.auth.login(this.email, this.password)
      .subscribe({
        next: () => this.router.navigate(['/home']),
        error: err => this.error = err.error?.error || 'Login failed'
      })
  }
}
