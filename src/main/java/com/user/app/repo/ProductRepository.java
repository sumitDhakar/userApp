package com.user.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.app.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

