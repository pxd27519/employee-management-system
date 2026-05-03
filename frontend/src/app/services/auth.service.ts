// src/app/services/auth.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { LoginRequest, LoginResponse } from '../models/employee.model';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private apiUrl = 'http://localhost:8081/api/auth';

  constructor(private http: HttpClient) {}

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, request).pipe(
      tap(response => {
        localStorage.setItem('token',    response.token);
        localStorage.setItem('role',     response.role);
        localStorage.setItem('username', response.username);
        localStorage.setItem('fullName', response.fullName);
      })
    );
  }

  logout(): void {
    localStorage.clear();
  }

  getToken(): string | null     { return localStorage.getItem('token'); }
  getRole(): string | null      { return localStorage.getItem('role'); }
  getUsername(): string | null  { return localStorage.getItem('username'); }
  getFullName(): string | null  { return localStorage.getItem('fullName'); }
  isLoggedIn(): boolean         { return !!this.getToken(); }
  isAdmin(): boolean            { return this.getRole() === 'ADMIN'; }
  isManager(): boolean          { return ['ADMIN','MANAGER'].includes(this.getRole() || ''); }
}
