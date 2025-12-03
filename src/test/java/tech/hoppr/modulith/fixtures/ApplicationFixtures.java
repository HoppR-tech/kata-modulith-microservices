package tech.hoppr.modulith.fixtures;

import tech.hoppr.modulith.order.model.OrderId;
import tech.hoppr.modulith.inventory.model.ProductRef;

public class ApplicationFixtures {

	public static OrderId ORDER_ID = OrderId.of("FR001");

	public static ProductRef PRODUCT_REF = ProductRef.of("123");

}
