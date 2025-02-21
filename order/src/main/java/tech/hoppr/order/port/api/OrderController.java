package tech.hoppr.order.port.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tech.hoppr.order.model.Item;
import tech.hoppr.order.model.OrderId;
import tech.hoppr.order.service.OrderService;
import tech.hoppr.shared.CustomerId;

import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/place")
	@ResponseStatus(code = ACCEPTED)
	public PlaceOrderResponse placeOrder(@RequestBody @Valid PlaceOrderRequest request) {
		CustomerId customerId = CustomerId.of(request.customerId());
		List<Item> items = request.toDomain();
		OrderId orderId = orderService.placeOrder(customerId, items);
		return new PlaceOrderResponse(orderId.value());
	}

	@PostMapping("/cancel/{id}")
	@ResponseStatus(code = NO_CONTENT)
	public void placeOrder(@PathVariable("id") String id) {
		orderService.cancel(OrderId.of(id));
	}

}
