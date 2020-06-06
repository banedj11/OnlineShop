package com.baka.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baka.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
