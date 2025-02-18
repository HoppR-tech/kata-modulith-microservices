package tech.hoppr.modulith.inventory.model;

import lombok.Builder;
import lombok.Singular;
import tech.hoppr.modulith.shared.ProductRef;
import tech.hoppr.modulith.shared.Quantity;

import java.util.Map;
import java.util.function.BiConsumer;

@Builder
public class Products {

	@Singular("addProduct")
	private final Map<ProductRef, Quantity> quantityPerProduct;

	public void forEach(BiConsumer<ProductRef, Quantity> consumer) {
		quantityPerProduct.forEach(consumer);
	}

}
