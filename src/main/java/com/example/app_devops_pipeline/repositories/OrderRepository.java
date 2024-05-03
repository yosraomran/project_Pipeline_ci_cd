package com.example.app_devops_pipeline.repositories;

import com.example.app_devops_pipeline.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
