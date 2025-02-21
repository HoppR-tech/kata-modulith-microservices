package tech.hoppr.order.fixtures;

import tech.hoppr.order.model.OrderId;
import tech.hoppr.shared.CustomerId;
import tech.hoppr.shared.ProductRef;
import tech.hoppr.shared.Quantity;

public class OrderFixtures {

	public static CustomerId CUSTOMER_ID = CustomerId.of("CUSTOMER_ID");

	public static OrderId ORDER_ID = OrderId.of("FR001");

	public static ProductRef PRODUCT_REF = ProductRef.of("123");

	public static ProductRef PRODUCT_REF_2 = ProductRef.of("456");

	public static Quantity QTY_TEN = Quantity.of(10);

}
