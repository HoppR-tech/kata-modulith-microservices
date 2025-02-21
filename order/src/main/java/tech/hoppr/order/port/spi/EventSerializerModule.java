package tech.hoppr.order.port.spi;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import tech.hoppr.order.model.OrderId;
import tech.hoppr.shared.CustomerId;
import tech.hoppr.shared.ProductRef;
import tech.hoppr.shared.Quantity;

import java.io.IOException;

public class EventSerializerModule extends SimpleModule {

	public EventSerializerModule() {
		addSerializer(OrderId.class, new JsonSerializer<>() {
			@Override
			public void serialize(OrderId orderId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
				jsonGenerator.writeString(orderId.value());
			}
		});
		addSerializer(CustomerId.class, new JsonSerializer<>() {
			@Override
			public void serialize(CustomerId customerId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
				jsonGenerator.writeString(customerId.value());
			}
		});
		addSerializer(ProductRef.class, new JsonSerializer<>() {
			@Override
			public void serialize(ProductRef productRef, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
				jsonGenerator.writeString(productRef.value());
			}
		});
		addSerializer(Quantity.class, new JsonSerializer<>() {
			@Override
			public void serialize(Quantity quantity, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
				jsonGenerator.writeNumber(quantity.value());
			}
		});
	}

}
