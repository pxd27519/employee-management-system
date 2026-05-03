import { Component, OnInit, Output, EventEmitter, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmployeeService } from '../../services/employee.service';
import { AuthService } from '../../services/auth.service';
import { Employee } from '../../models/employee.model';

@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {
  @Output() editEmp = new EventEmitter<Employee>();
  @Output() addEmp  = new EventEmitter<void>();

  employees: Employee[] = [];
  allEmployees: Employee[] = [];
  departments: string[] = [];
  loading = false;
  searchTerm = '';
  selectedDept = '';
  canCreate = false;
  isAdmin = false;

  constructor(
    private empService: EmployeeService,
    private authService: AuthService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.canCreate = this.authService.isManager();
    this.isAdmin   = this.authService.isAdmin();
    this.loadEmployees();
  }

  loadEmployees(): void {
    this.empService.getAll().subscribe({
      next: (data) => {
        this.employees    = [...data];
        this.allEmployees = [...data];
        this.departments  = [...new Set(data.map(e => e.department))];
        this.cdr.detectChanges();  // ← yeh add kiya
      },
      error: (err) => { console.log('Error:', err); }
    });
  }

  onSearch(): void {
    if (!this.searchTerm.trim()) { this.employees = this.allEmployees; return; }
    this.empService.search(this.searchTerm).subscribe(data => {
      this.employees = [...data];
      this.cdr.detectChanges();
    });
  }

  onDeptFilter(): void {
    if (!this.selectedDept) { this.employees = this.allEmployees; return; }
    this.empService.getByDepartment(this.selectedDept).subscribe(data => {
      this.employees = [...data];
      this.cdr.detectChanges();
    });
  }

  editEmployee(emp: Employee): void { this.editEmp.emit(emp); }
  openAddForm(): void { this.addEmp.emit(); }

  deleteEmployee(id: number): void {
    if (!confirm('Delete this employee?')) return;
    this.empService.delete(id).subscribe(() => this.loadEmployees());
  }
}