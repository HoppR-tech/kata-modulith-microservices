package tech.hoppr.microservice.inventory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tech.hoppr.microservice.inventory.service.Decrement;
import tech.hoppr.microservice.inventory.service.InventoryService;
import tech.hoppr.microservice.inventory.model.ProductRef;
import tech.hoppr.microservice.inventory.model.Quantity;

import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@RequestMapping("/inventories")
@RequiredArgsConstructor
public class OnOrderPlacedWebHook {

	private final InventoryService inventoryService;

	@PostMapping("/on-order-placed")
	@ResponseStatus(code = ACCEPTED)
	public void placeOrder(OrderPlacedDto request) {
		inventoryService.accept(Decrement.builder()
			.productsToDecrement(request.items().stream()
				.map(item -> Decrement.ProductToDecrement.builder()
					.ref(ProductRef.of(item.productRef()))
					.quantity(Quantity.of(item.quantity()))
					.build())
				.toList())
			.build());
	}

	public record OrderPlacedDto(String orderId, List<ItemDto> items) {

		record ItemDto(String productRef, int quantity) {
		}

	}

}
