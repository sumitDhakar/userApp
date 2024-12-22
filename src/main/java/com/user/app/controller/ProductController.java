package com.user.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.app.model.Product;
import com.user.app.payload.ProductRequest;
import com.user.app.service.IProductService;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private IProductService productService;

	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@RequestBody ProductRequest request) {
		return ResponseEntity.ok(this.productService.createProduct(request));
	}

	@GetMapping("/products")
	public ResponseEntity<List<Product>> listProducts() {
		return ResponseEntity.ok(productService.listProducts());
	}
}
