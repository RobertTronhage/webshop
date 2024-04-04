package se.tronhage.webshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.tronhage.webshop.entity.Order;
import se.tronhage.webshop.enums.OrderStatus;
import se.tronhage.webshop.repository.OrderRepo;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepo orderRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public List<Order> findAllConfirmedOrders(){
        return orderRepo.findByStatus(OrderStatus.CONFIRMED);
    }

    public List<Order> findAllShippedOrders(){
        return orderRepo.findByStatus(OrderStatus.SHIPPED);
    }
}
