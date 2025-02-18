package tech.hoppr.modulith.inventory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import tech.hoppr.modulith.inventory.model.Products;
import tech.hoppr.modulith.inventory.service.InventoryService;
import tech.hoppr.modulith.order.model.OrderPlaced;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderPlacedListener {

	private final InventoryService inventoryService;

	@EventListener(OrderPlaced.class)
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
