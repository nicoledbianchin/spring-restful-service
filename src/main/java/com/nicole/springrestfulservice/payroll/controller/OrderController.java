package com.nicole.springrestfulservice.payroll.controller;

import com.nicole.springrestfulservice.payroll.models.Order;
import com.nicole.springrestfulservice.payroll.models.Status;
import com.nicole.springrestfulservice.payroll.models.assembler.OrderModelAssembler;
import com.nicole.springrestfulservice.payroll.repository.OrderRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderModelAssembler assembler;

    public OrderController(OrderRepository orderRepository, OrderModelAssembler assembler) {
        this.orderRepository = orderRepository;
        this.assembler = assembler;
    }

    @GetMapping("/orders")
    CollectionModel<EntityModel<Order>> getAll() {
        List<EntityModel<Order>> orders = orderRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(orders, linkTo(methodOn(OrderController.class).getAll()).withSelfRel());
    }

    @GetMapping("/orders/{id}")
    EntityModel<Order> findById(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        return assembler.toModel(order);
    }

    @PostMapping("/orders")
    ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order order) {
        order.setStatus(Status.IN_PROGRESS);
        Order newOrder = orderRepository.save(order);

        return ResponseEntity.created(linkTo(methodOn(OrderController.class).findById(newOrder.getId())).toUri())
                .body(assembler.toModel(newOrder));
    }
}
