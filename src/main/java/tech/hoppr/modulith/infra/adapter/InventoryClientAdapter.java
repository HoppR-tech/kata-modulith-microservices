package tech.hoppr.modulith.infra.adapter;

import java.net.http.HttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import tech.hoppr.modulith.order.model.OrderPlaced;
import tech.hoppr.modulith.shared.MessageBus;
import tech.hoppr.modulith.shared.SpringMessageBus;

public class InventoryClientAdapter {

    @Autowired
    MessageBus springMessageBus;

    public void ReverseProduct(OrderPlaced event) {
        if CircuitBraker.getOpen() {
            springMessageBus.emit(event);
        }

        try {
            // Http Call to Inventory
        }
        catch(Exception e) {
            CircuitBraker.Open();
        }
    }
    
}
