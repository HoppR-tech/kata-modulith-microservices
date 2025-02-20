package tech.hoppr.modulith.order.model;

public class WithdrawalPeriodExceeded extends OrderException {

	public WithdrawalPeriodExceeded(OrderId orderId) {
		super("Cannot cancel order '%s' because the withdrawal period has been exceeded".formatted(orderId.value()));
	}

}
