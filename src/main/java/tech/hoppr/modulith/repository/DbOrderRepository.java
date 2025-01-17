package tech.hoppr.modulith.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import tech.hoppr.modulith.model.Order;

@RequiredArgsConstructor
public class DbOrderRepository implements OrderRepository {

    private final JPAQueryFactory factory;

    @Override
    public void save(Order order) {

    }
}
