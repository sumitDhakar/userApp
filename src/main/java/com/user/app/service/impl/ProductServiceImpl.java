package com.user.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.app.exception.ProductNotFoundException;
import com.user.app.exception.ResourceNotFoundException;
import com.user.app.exception.UserNotFoundException;
import com.user.app.model.Product;
import com.user.app.model.User;
import com.user.app.payload.ProductRequest;
import com.user.app.repo.ProductRepository;
import com.user.app.repo.UserRepository;
import com.user.app.service.IProductService;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;

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
	
	  @Override
	    public void buyProduct(Long productId, String username) {
	        Product product = productRepository.findById(productId)
	                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
	        System.out.println(productId);
	        User user = userRepository.findByname(username)
	                .orElseThrow(() -> new UserNotFoundException("User not found"));
	        System.out.println(username);
	        System.out.println("User " + username + " bought product " + product.getName());
	    }
	
	public double getProductPrice(long productId) {
		Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException());
		return product.getPrice();	}

}
