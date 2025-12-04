package tech.hoppr.microservice.inventory.shared;

public interface MessageEmitter {

	void emit(Object message);

}
