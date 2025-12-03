package tech.hoppr.modulith.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tech.hoppr.modulith.order.model.Item;
import tech.hoppr.modulith.order.service.OrderService;
import tech.hoppr.modulith.shared.OrderId;

import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/place")
	@ResponseStatus(code = ACCEPTED)
	public void place(@RequestBody @Valid PlaceOrderDto request) {
		List<Item> items = request.toDomain();
		orderService.placeOrder(items);
	}

	@PostMapping("/cancel")
	@ResponseStatus(code = ACCEPTED)
	public void cancel(@RequestBody @Valid CancelOrderDto request) {
		orderService.cancelOrder(OrderId.of(request.orderId()));
	}

}
