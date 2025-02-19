package tech.hoppr.inventory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import tech.hoppr.inventory.model.Products;
import tech.hoppr.inventory.service.InventoryService;
import tech.hoppr.shared.ProductRef;
import tech.hoppr.shared.Quantity;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderPlacedListener {

	private final InventoryService inventoryService;
	private final MessageDeserializer deserializer;

	@RabbitListener(queues = "order.placed")
	public void handle(String message) {
		try {
			log.info("Event received: {}", message);
			OrderPlaced event = deserializer.deserialize(message, OrderPlaced.class);
			Products products = toProducts(event.items());
			inventoryService.decrement(products);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	private Products toProducts(List<OrderPlaced.Item> items) {
		Products.ProductsBuilder builder = Products.builder();
		items.forEach(item -> builder.addProduct(
				item.productRef(),
				item.quantity()));
		return builder.build();
	}

}
