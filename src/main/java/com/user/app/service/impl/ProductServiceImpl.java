package com.user.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.app.exception.ResourceNotFoundException;
import com.user.app.model.Product;
import com.user.app.payload.ProductRequest;
import com.user.app.repo.ProductRepository;
import com.user.app.service.IProductService;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> listProducts() {

		return this.productRepository.findAll();
	}

	@Override
	public Product createProduct(ProductRequest request) {

		Product product = new Product();
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		return this.productRepository.save(product);
	}
	
	
	public double getProductPrice(long productId) {
		Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException());
		return product.getPrice();	}

}
