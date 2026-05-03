package com.pooja.ems.service;

import com.pooja.ems.model.Employee;
import com.pooja.ems.model.Role;
import com.pooja.ems.model.User;
import com.pooja.ems.repository.EmployeeRepository;
import com.pooja.ems.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            seedUsers();
            seedEmployees();
            log.info("✅ Sample data seeded successfully!");
        }
    }

    private void seedUsers() {
        userRepository.save(User.builder()
                .username("admin").fullName("Admin User")
                .email("admin@ems.com").role(Role.ADMIN)
                .password(passwordEncoder.encode("admin123")).build());

        userRepository.save(User.builder()
                .username("manager").fullName("Manager User")
                .email("manager@ems.com").role(Role.MANAGER)
                .password(passwordEncoder.encode("manager123")).build());

        userRepository.save(User.builder()
                .username("employee").fullName("Employee User")
                .email("employee@ems.com").role(Role.EMPLOYEE)
                .password(passwordEncoder.encode("emp123")).build());

        log.info("Users seeded — admin/admin123, manager/manager123, employee/emp123");
    }

    private void seedEmployees() {
        employeeRepository.save(Employee.builder()
                .firstName("Rahul").lastName("Sharma")
                .email("rahul.sharma@ems.com").department("Engineering")
                .designation("Senior Developer").salary(85000.0)
                .joiningDate(LocalDate.of(2021, 3, 15)).role(Role.EMPLOYEE).build());

        employeeRepository.save(Employee.builder()
                .firstName("Priya").lastName("Patel")
                .email("priya.patel@ems.com").department("Engineering")
                .designation("Tech Lead").salary(120000.0)
                .joiningDate(LocalDate.of(2019, 7, 1)).role(Role.MANAGER).build());

        employeeRepository.save(Employee.builder()
                .firstName("Amit").lastName("Kumar")
                .email("amit.kumar@ems.com").department("HR")
                .designation("HR Manager").salary(75000.0)
                .joiningDate(LocalDate.of(2020, 1, 10)).role(Role.MANAGER).build());

        employeeRepository.save(Employee.builder()
                .firstName("Sneha").lastName("Joshi")
                .email("sneha.joshi@ems.com").department("Finance")
                .designation("Finance Analyst").salary(65000.0)
                .joiningDate(LocalDate.of(2022, 6, 20)).role(Role.EMPLOYEE).build());

        employeeRepository.save(Employee.builder()
                .firstName("Pooja").lastName("Dixit")
                .email("pooja.dixit@ems.com").department("Engineering")
                .designation("Java Developer").salary(72000.0)
                .joiningDate(LocalDate.of(2023, 11, 1)).role(Role.EMPLOYEE).build());

        log.info("5 sample employees seeded!");
    }
}
