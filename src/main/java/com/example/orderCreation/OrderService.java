package com.example.orderCreation;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }
    public Order createOrder(List<OrderController.OrderRequest.OrderItemRequest> items){
        Order order=new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        BigDecimal totalAmount= BigDecimal.ZERO;
        for (OrderController.OrderRequest.OrderItemRequest itemRequest:items) {
    OrderItem item = new OrderItem();
    item.setProductCode(itemRequest.getProductCode());
    item.setQuantity(itemRequest.getQuantity());
    item.setUnitPrice(BigDecimal.valueOf(itemRequest.getUnitPrice()));
    item.setOrder(order);
    order.getItems().add(item);
    totalAmount=totalAmount.add(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
}
        order.setTotalAmount(totalAmount);
         return orderRepository.save(order);

    }
}
