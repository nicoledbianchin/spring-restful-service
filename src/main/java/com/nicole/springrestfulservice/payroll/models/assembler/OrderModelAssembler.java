package com.nicole.springrestfulservice.payroll.models.assembler;

import com.nicole.springrestfulservice.payroll.controller.OrderController;
import com.nicole.springrestfulservice.payroll.models.Order;
import com.nicole.springrestfulservice.payroll.models.Status;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

    @Override
    public EntityModel<Order> toModel(Order order) {
        EntityModel<Order> orderModel = EntityModel.of(order, linkTo(methodOn(OrderController.class).findById(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).getAll).withRel("orders"));

        if (order.getStatus() == Status.IN_PROGRESS) {
            orderModel.add(linkTo(methodOn(OrderController.class).cancel(order.getId())).withRel("cancel"));
            orderModel.add(linkTo(methodOn(OrderController.class).complete(order.getId())).withRel("complete"));
        }
    }
}
