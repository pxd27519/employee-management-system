import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { Employee } from '../../models/employee.model';
import { NavbarComponent } from '../navbar/navbar.component';
import { EmployeeListComponent } from '../employee-list/employee-list.component';
import { EmployeeFormComponent } from '../employee-form/employee-form.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, NavbarComponent, EmployeeListComponent, EmployeeFormComponent],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  @ViewChild(EmployeeListComponent) listComp!: EmployeeListComponent;
  fullName = '';
  role = '';
  showForm = false;
  selectedEmployee: Employee | null = null;

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.fullName = this.authService.getFullName() || '';
    this.role     = this.authService.getRole() || '';
  }

  onEditEmp(emp: Employee): void { this.selectedEmployee = emp; this.showForm = true; }
  onAddEmp(): void { this.selectedEmployee = null; this.showForm = true; }
  onFormSaved(): void { this.showForm = false; this.listComp.loadEmployees(); }
  onFormCancelled(): void { this.showForm = false; }
}