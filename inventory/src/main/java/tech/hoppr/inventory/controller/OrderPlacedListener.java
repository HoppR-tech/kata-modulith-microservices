package tech.hoppr.inventory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import tech.hoppr.inventory.model.Products;
import tech.hoppr.inventory.service.InventoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderPlacedListener {

	private final InventoryService inventoryService;

	@RabbitListener(queues = "inventory.order_placed")
	public void handle(OrderPlaced event) {
		Products products = toProducts(event.items());
		inventoryService.decrement(products);
	}

	private Products toProducts(List<OrderPlaced.Item> items) {
		Products.ProductsBuilder builder = Products.builder();
		items.forEach(item -> builder.addProduct(item.productRef(), item.quantity()));
		return builder.build();
	}

}
