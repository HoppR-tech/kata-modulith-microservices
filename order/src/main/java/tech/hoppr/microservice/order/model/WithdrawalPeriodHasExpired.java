package tech.hoppr.microservice.order.model;

import lombok.Getter;
import tech.hoppr.microservice.order.shared.OrderId;

public class WithdrawalPeriodHasExpired extends OrderException {

	@Getter
	private OrderId orderId;

	public WithdrawalPeriodHasExpired(OrderId orderId) {
		super("The withdrawal period has expired.");
		this.orderId = orderId;
	}
}
