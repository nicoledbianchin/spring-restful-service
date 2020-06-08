package com.nicole.springrestfulservice.payroll.repository;

import com.nicole.springrestfulservice.payroll.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
