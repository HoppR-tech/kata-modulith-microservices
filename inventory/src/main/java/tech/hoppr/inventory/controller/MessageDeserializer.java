package tech.hoppr.inventory.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class MessageDeserializer {

	private static final ObjectMapper mapper = new ObjectMapper()
		.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	@SneakyThrows
	public <T> T deserialize(String message, Class<T> messageType) {
		return mapper.readValue(message, messageType);
	}

}
