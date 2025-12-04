package tech.hoppr.microservice.order.model;

public class OrderException extends RuntimeException {

	public OrderException(String message) {
		super(message);
	}

}
