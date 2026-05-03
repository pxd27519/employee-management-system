import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  fullName = '';
  role = '';
  roleClass = '';

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.fullName = this.authService.getFullName() || '';
    this.role     = this.authService.getRole() || '';
    this.roleClass = this.role.toLowerCase();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}