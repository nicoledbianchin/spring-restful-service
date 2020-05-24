package com.nicole.springrestfulservice.payroll.repository;

import com.nicole.springrestfulservice.payroll.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
