package com.example.app_devops_pipeline.repositories;

import com.example.app_devops_pipeline.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
