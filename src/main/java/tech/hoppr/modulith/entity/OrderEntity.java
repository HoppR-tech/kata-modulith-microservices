package tech.hoppr.modulith.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ORDER")
@RequiredArgsConstructor
@Getter
public class OrderEntity {

    @Id
    @Column(name = "ID")
    private UUID id;

    @OneToMany(mappedBy = "order")
    private List<OrderItemEntity> items;

}
