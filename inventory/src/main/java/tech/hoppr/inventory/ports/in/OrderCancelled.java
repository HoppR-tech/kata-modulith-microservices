package tech.hoppr.inventory.ports.in;

import lombok.Builder;

@Builder
public record OrderCancelled(String orderId) {
}
