package tech.hoppr.modulith.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.modulith.order.model.Item;
import tech.hoppr.modulith.order.model.OrderId;
import tech.hoppr.modulith.inventory.model.Products;
import tech.hoppr.modulith.inventory.model.Reservation;
import tech.hoppr.modulith.inventory.repository.InventoryRepository;

import java.util.List;

@RequiredArgsConstructor
public class InventoryService {

	private final InventoryRepository inventories;

	@Transactional
    public void reserve(OrderId orderId, List<Item> items) {
		Products products = toProducts(items);
		Reservation reservation = Reservation.create(orderId, products);
		inventories.save(reservation);
    }

	private Products toProducts(List<Item> items) {
		Products.ProductsBuilder builder = Products.builder();
		items.forEach(item -> builder.addProduct(item.product(), item.quantity()));
		return builder.build();
	}
}
