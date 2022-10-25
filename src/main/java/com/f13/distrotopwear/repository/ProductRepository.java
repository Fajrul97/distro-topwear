package com.f13.distrotopwear.repository;

import com.f13.distrotopwear.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer > {
  Optional<Product> findByProductId(String productId);
}
