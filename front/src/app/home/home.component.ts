import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})

export class HomeComponent implements OnInit {
  users: any[] = [];

  constructor(private auth: AuthService, private http: HttpClient) {}

  ngOnInit() {
      this.http.get<any[]>('/api/users').subscribe(data => this.users = data);
  }

  logout() {
    this.auth.logout();
  }
}
