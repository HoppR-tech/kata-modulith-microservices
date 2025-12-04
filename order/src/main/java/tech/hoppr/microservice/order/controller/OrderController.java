package tech.hoppr.microservice.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tech.hoppr.microservice.order.controller.CancelOrderDto;
import tech.hoppr.microservice.order.controller.PlaceOrderDto;
import tech.hoppr.microservice.order.model.Item;
import tech.hoppr.microservice.order.service.OrderService;
import tech.hoppr.microservice.order.shared.OrderId;

import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@GetMapping("/{orderId}")
	@ResponseStatus(code = OK)
	public OrderDto getOrder(@PathVariable String orderId, @RequestParam(required = false) String timeZone) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}

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
