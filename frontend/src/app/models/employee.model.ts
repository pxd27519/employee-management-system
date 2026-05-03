// src/app/models/employee.model.ts
export interface Employee {
  id?: number;
  firstName: string;
  lastName: string;
  email: string;
  department: string;
  designation: string;
  salary: number;
  joiningDate: string;
  role: 'ADMIN' | 'MANAGER' | 'EMPLOYEE';
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  username: string;
  role: string;
  fullName: string;
}
