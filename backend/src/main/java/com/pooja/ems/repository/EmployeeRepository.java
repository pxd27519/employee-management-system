package com.pooja.ems.repository;

import com.pooja.ems.model.Employee;
import com.pooja.ems.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Find by department — uses idx_department index
    List<Employee> findByDepartment(String department);

    // Find by role
    List<Employee> findByRole(Role role);

    // Find by email — uses idx_email index
    Optional<Employee> findByEmail(String email);

    // Search by name (first or last)
    @Query("SELECT e FROM Employee e WHERE " +
           "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
           "LOWER(e.lastName)  LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Employee> searchByName(@Param("name") String name);

    // Department-wise salary stats
    @Query("SELECT e.department, COUNT(e), AVG(e.salary), MIN(e.salary), MAX(e.salary) " +
           "FROM Employee e GROUP BY e.department")
    List<Object[]> getDepartmentSalaryStats();

    boolean existsByEmail(String email);
}
