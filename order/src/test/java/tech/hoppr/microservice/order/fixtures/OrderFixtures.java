package tech.hoppr.microservice.order.fixtures;

import tech.hoppr.microservice.order.model.OrderId;
import tech.hoppr.microservice.order.model.ProductRef;

public class OrderFixtures {

	public static OrderId ORDER_ID = OrderId.of("FR001");

	public static ProductRef PRODUCT_REF = ProductRef.of("123");

}
