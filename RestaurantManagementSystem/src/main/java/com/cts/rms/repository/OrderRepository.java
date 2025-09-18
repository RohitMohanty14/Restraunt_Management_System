package com.cts.rms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.rms.model.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
	
}
