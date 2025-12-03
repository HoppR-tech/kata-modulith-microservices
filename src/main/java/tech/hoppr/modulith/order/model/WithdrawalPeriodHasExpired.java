package tech.hoppr.modulith.order.model;

import lombok.Getter;
import tech.hoppr.modulith.shared.OrderId;

public class WithdrawalPeriodHasExpired extends OrderException {

	@Getter
	private OrderId orderId;

	public WithdrawalPeriodHasExpired(OrderId orderId) {
		super("The withdrawal period has expired.");
		this.orderId = orderId;
	}
}
