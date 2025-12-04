package tech.hoppr.microservice.order.model;

import lombok.Getter;

public class WithdrawalPeriodHasExpired extends OrderException {

	@Getter
	private OrderId orderId;

	public WithdrawalPeriodHasExpired(OrderId orderId) {
		super("The withdrawal period has expired.");
		this.orderId = orderId;
	}
}
