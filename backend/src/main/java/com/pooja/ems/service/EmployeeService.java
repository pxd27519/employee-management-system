package com.pooja.ems.service;

import com.pooja.ems.dto.EmployeeDto;
import com.pooja.ems.exception.ResourceNotFoundException;
import com.pooja.ems.model.Employee;
import com.pooja.ems.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    // ── CRUD ────────────────────────────────────────────

    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public EmployeeDto getEmployeeById(Long id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return toDto(emp);
    }

    public EmployeeDto createEmployee(EmployeeDto dto) {
        if (employeeRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Employee with email already exists: " + dto.getEmail());
        }
        Employee saved = employeeRepository.save(toEntity(dto));
        return toDto(saved);
    }

    public EmployeeDto updateEmployee(Long id, EmployeeDto dto) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        existing.setDepartment(dto.getDepartment());
        existing.setDesignation(dto.getDesignation());
        existing.setSalary(dto.getSalary());
        existing.setJoiningDate(dto.getJoiningDate());
        existing.setRole(dto.getRole());

        return toDto(employeeRepository.save(existing));
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found: " + id);
        }
        employeeRepository.deleteById(id);
    }

    // ── Search & Filter ─────────────────────────────────

    public List<EmployeeDto> getByDepartment(String department) {
        return employeeRepository.findByDepartment(department)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<EmployeeDto> searchByName(String name) {
        return employeeRepository.searchByName(name)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    // ── Mapper ──────────────────────────────────────────

    private EmployeeDto toDto(Employee e) {
        return EmployeeDto.builder()
                .id(e.getId())
                .firstName(e.getFirstName())
                .lastName(e.getLastName())
                .email(e.getEmail())
                .department(e.getDepartment())
                .designation(e.getDesignation())
                .salary(e.getSalary())
                .joiningDate(e.getJoiningDate())
                .role(e.getRole())
                .build();
    }

    private Employee toEntity(EmployeeDto dto) {
        return Employee.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .department(dto.getDepartment())
                .designation(dto.getDesignation())
                .salary(dto.getSalary())
                .joiningDate(dto.getJoiningDate())
                .role(dto.getRole())
                .build();
    }
}
