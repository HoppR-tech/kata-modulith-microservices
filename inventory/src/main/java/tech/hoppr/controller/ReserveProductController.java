package tech.hoppr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.hoppr.service.OrderService;
import tech.hoppr.model.OrderId;
import tech.hoppr.model.Product;
import tech.hoppr.shared.OrderPlaced;

import java.util.List;

@RestController
public class ReserveProductController {

    @Autowired
    private final InventoryService inventoryService;
    
    @PostMapping("/reserve-products")
    public void handle(@RequestBody OrderPlaced event) {
        ReserveProducts reserveProducts = toCommand(event);
		inventoryService.handle(reserveProducts);
	}

	private ReserveProducts toCommand(OrderPlaced event) {
		ReserveProducts.Builder builder = ReserveProducts.builder();
		event.items().forEach(item -> builder.product(item.product(), item.quantity()));
		return builder
			.orderId(event.orderId())
			.build();
	}
}
