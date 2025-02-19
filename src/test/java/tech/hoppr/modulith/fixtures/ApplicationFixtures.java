package tech.hoppr.modulith.fixtures;

import tech.hoppr.modulith.model.OrderId;
import tech.hoppr.modulith.model.ProductRef;
import tech.hoppr.modulith.model.Quantity;

public class ApplicationFixtures {

	public static OrderId ORDER_ID = OrderId.of("FR001");

	public static ProductRef PRODUCT_REF = ProductRef.of("123");

	public static ProductRef PRODUCT_REF_2 = ProductRef.of("456");

	public static Quantity QTY_ONE = Quantity.of(1);

	public static Quantity QTY_TEN = Quantity.of(10);

}
