package com.nicole.springrestfulservice.payroll.models.assembler;

import com.nicole.springrestfulservice.payroll.controller.EmployeeController;
import com.nicole.springrestfulservice.payroll.models.Employee;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmployeeModelAssemble implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {
    
    @Override
    public EntityModel<Employee> toModel(Employee employee) {
        return EntityModel.of(employee, linkTo(methodOn(EmployeeController.class).findById(employee.getId())).withSelfRel(), linkTo(methodOn(EmployeeController.class).getAll()).withRel("employees"));
    }
}
