package tech.hoppr.fixtures;

import tech.hoppr.shared.OrderId;
import tech.hoppr.shared.CustomerId;
import tech.hoppr.shared.ProductRef;
import tech.hoppr.shared.Quantity;

public class ApplicationFixtures {

	public static CustomerId CUSTOMER_ID = CustomerId.of("CUSTOMER_ID");

	public static OrderId ORDER_ID = OrderId.of("FR001");

	public static OrderId ORDER_ID_2 = OrderId.of("FR002");

	public static ProductRef PRODUCT_REF = ProductRef.of("123");

	public static ProductRef PRODUCT_REF_2 = ProductRef.of("456");

	public static Quantity QTY_ONE = Quantity.of(1);

	public static Quantity QTY_TEN = Quantity.of(10);

}
