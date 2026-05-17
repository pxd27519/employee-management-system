package com.pooja.ems.controller;

import com.pooja.ems.dto.EmployeeDto;
import com.pooja.ems.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Employees", description = "Employee CRUD operations with RBAC")
public class EmployeeController {

    private final EmployeeService employeeService;

    // ── GET ALL — Admin, Manager, Employee (all roles) ──
    @GetMapping
    @Operation(summary = "Get all employees — All roles")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // ── GET BY ID ────────────────────────────────────────
    @GetMapping("/{id}")
    @Operation(summary = "Get employee by ID — All roles")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    // ── CREATE — Admin and Manager only ─────────────────
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Create employee — Admin/Manager only")
    public ResponseEntity<EmployeeDto> createEmployee(
            @Valid @RequestBody EmployeeDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeeService.createEmployee(dto));
    }

    // ── UPDATE — Admin and Manager only ─────────────────
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Update employee — Admin/Manager only")
    public ResponseEntity<EmployeeDto> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeDto dto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, dto));
    }

    // ── DELETE — Admin only ──────────────────────────────
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete employee — Admin only")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    // ── SEARCH by department ─────────────────────────────
    @GetMapping("/department/{dept}")
    @Operation(summary = "Get employees by department")
    public ResponseEntity<List<EmployeeDto>> getByDepartment(
            @PathVariable String dept) {
        return ResponseEntity.ok(employeeService.getByDepartment(dept));
    }

    // ── SEARCH by name ───────────────────────────────────
    @GetMapping("/search")
    @Operation(summary = "Search employees by name")
    public ResponseEntity<List<EmployeeDto>> search(
            @RequestParam String name) {
        return ResponseEntity.ok(employeeService.searchByName(name));
    }
}
