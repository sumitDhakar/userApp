package com.user.app.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.user.app.exception.ResourceNotFoundException;
import com.user.app.model.Product;
import com.user.app.repo.ProductRepository;

public class ProductPriceWebSocketHandler extends TextWebSocketHandler {

	private Map<Long, Double> productPrices = new HashMap<>();

	private ProductServiceImpl productServiceImpl;
	
public ProductPriceWebSocketHandler(ProductServiceImpl productServiceImpl) {
 this.productServiceImpl=productServiceImpl;
}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		   String clientMessage = message.getPayload();
	        System.out.println("Received: " + clientMessage);
		Long productId = Long.parseLong(session.getUri().getPath().split("/")[3]);
System.out.println(productId);
		double newPrice = getProductPrice(productId);
		productPrices.put(productId, newPrice);

		String response = "{ \"product_id\": " + productId + ", \"real_time_price\": " + newPrice + " }";
		session.sendMessage(new TextMessage(response));
	}

	private double getProductPrice(Long productId) {
		return this.productServiceImpl.getProductPrice(productId);
	}
}
