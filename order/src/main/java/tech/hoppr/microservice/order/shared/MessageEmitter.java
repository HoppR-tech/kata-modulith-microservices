package tech.hoppr.microservice.order.shared;

public interface MessageEmitter {

	void emit(Object message);

}
