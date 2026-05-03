import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmployeeService } from '../../services/employee.service';
import { Employee } from '../../models/employee.model';

@Component({
  selector: 'app-employee-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './employee-form.component.html',
  styleUrls: ['./employee-form.component.css']
})
export class EmployeeFormComponent implements OnInit {
  @Input()  editData: Employee | null = null;
  @Output() saved     = new EventEmitter<void>();
  @Output() cancelled = new EventEmitter<void>();

  employee: Employee = {
    firstName: '', lastName: '', email: '',
    department: '', designation: '', salary: 0,
    joiningDate: '', role: 'EMPLOYEE'
  };

  isEdit = false;
  loading = false;
  errorMsg = '';

  constructor(private empService: EmployeeService) {}

  ngOnInit(): void {
    if (this.editData) { this.isEdit = true; this.employee = { ...this.editData }; }
  }

  onSubmit(): void {
    this.loading = true;
    this.errorMsg = '';
    const action = this.isEdit
      ? this.empService.update(this.employee.id!, this.employee)
      : this.empService.create(this.employee);
    action.subscribe({
      next: () => { this.loading = false; this.saved.emit(); },
      error: (err) => { this.loading = false; this.errorMsg = err.error?.message || 'Error'; }
    });
  }

  onCancel(): void { this.cancelled.emit(); }
}