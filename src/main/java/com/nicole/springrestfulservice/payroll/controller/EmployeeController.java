package com.nicole.springrestfulservice.payroll.controller;

import com.nicole.springrestfulservice.payroll.exception.EmployeeNotFoundException;
import com.nicole.springrestfulservice.payroll.models.Employee;
import com.nicole.springrestfulservice.payroll.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository repository){
        this.repository = repository;
    }

    @GetMapping("/employees")
    List<Employee> getAll() {
        return repository.findAll();
    }

    @PostMapping("/employees")
    Employee addNewEmployee(@RequestBody Employee newEmployee) {
        return repository.save(newEmployee);
    }

    @GetMapping("/employees/{id}")
    Employee findById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() ->new EmployeeNotFoundException(id));
    }

    @PutMapping("/employees/{id}")
    Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable long id) {
        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });
    }

    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
