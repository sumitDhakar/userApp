package com.user.app.service;

import java.util.List;

import com.user.app.model.Product;
import com.user.app.payload.ProductRequest;

public interface IProductService {

	public List<Product> listProducts();

	public Product createProduct(ProductRequest request);
	
	 public void buyProduct(Long productId, String username);

}
