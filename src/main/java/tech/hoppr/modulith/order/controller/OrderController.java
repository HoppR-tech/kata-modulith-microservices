package tech.hoppr.modulith.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tech.hoppr.modulith.order.model.Item;
import tech.hoppr.modulith.order.model.OrderId;
import tech.hoppr.modulith.order.service.OrderService;

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
		List<Item> items = request.toDomain();
		OrderId orderId = orderService.placeOrder(items);
		return new PlaceOrderResponse(orderId.value());
	}

	@PostMapping("/cancel/{id}")
	@ResponseStatus(code = NO_CONTENT)
	public void placeOrder(@PathVariable("id") String id) {
		orderService.cancel(OrderId.of(id));
	}

}
