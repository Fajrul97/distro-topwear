package com.f13.distrotopwear.repository;

import com.f13.distrotopwear.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String > {
  Optional<Category> findByCategoryId(String categoryId);
}
