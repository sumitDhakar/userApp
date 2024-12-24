package com.user.app.controller;

import java.security.Principal;
import java.util.List;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.app.model.Product;
import com.user.app.model.User;
import com.user.app.payload.ProductRequest;
import com.user.app.payload.PurchaseResponse;
import com.user.app.service.IProductService;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private IProductService productService;

	/**
	 * Creates a new product.
	 * 
	 * @param request The request object containing product details.
	 * @return ResponseEntity with the created product.
	 */

	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@RequestBody ProductRequest request) {
		return ResponseEntity.ok(this.productService.createProduct(request));
	}
	 /**
     * Lists all products.
     * 
     * @return ResponseEntity containing a list of products.
     */
	@GetMapping("/products")
	public ResponseEntity<List<Product>> listProducts() {
		return ResponseEntity.ok(productService.listProducts());
	}
	/**
     * Simulates purchasing a product by its ID.
     * 
     * @param product_id The ID of the product to be purchased.
     * @param p The authenticated principal (user) who is making the purchase.
     * @return ResponseEntity with a success or error message based on the outcome.
     */
	@PostMapping("/buy/{product_id}")
	public ResponseEntity<?> buyProduct(@PathVariable Long product_id, Principal p) {
		try {

			productService.buyProduct(product_id, p.getName());
			return ResponseEntity.ok(new PurchaseResponse("Purchase successful", product_id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new PurchaseResponse("Purchase successful", product_id));
		}
	}
}
