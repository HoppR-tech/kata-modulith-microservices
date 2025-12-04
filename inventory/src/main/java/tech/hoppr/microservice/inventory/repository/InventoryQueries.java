package tech.hoppr.microservice.inventory.repository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class InventoryQueries {

	private final JpaInventories repository;

	public List<CheckedProduct> check() {
		return repository.findAll().stream()
			.map(entity -> new CheckedProduct(entity.getProductRef(), entity.getQuantity()))
			.toList();
	}

	// C'est une projection
	public record CheckedProduct(String productRef, int quantity) {}

}
