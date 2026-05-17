package com.pooja.ems.dto;

import com.pooja.ems.model.Role;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    private Long id;

    @NotBlank(message = "First name required")
    private String firstName;

    @NotBlank(message = "Last name required")
    private String lastName;

    @Email(message = "Valid email required")
    @NotBlank
    private String email;

    @NotBlank(message = "Department required")
    private String department;

    @NotBlank(message = "Designation required")
    private String designation;

    @NotNull @Positive(message = "Salary must be positive")
    private Double salary;

    @NotNull
    private LocalDate joiningDate;

    private Role role;
}
