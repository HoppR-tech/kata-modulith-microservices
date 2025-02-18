package tech.hoppr.inventory.model;

import lombok.Builder;
import lombok.Singular;
import tech.hoppr.shared.ProductRef;
import tech.hoppr.shared.Quantity;

import java.util.Map;
import java.util.function.BiConsumer;

@Builder
public class Products {

	@Singular("addProduct")
	private Map<ProductRef, Quantity> quantityPerProducts;

	public void forEach(BiConsumer<ProductRef, Quantity> consumer) {
		quantityPerProducts.forEach(consumer);
	}
}
