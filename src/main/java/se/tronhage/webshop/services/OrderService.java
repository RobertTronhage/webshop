package se.tronhage.webshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.tronhage.webshop.entity.Order;
import se.tronhage.webshop.enums.OrderStatus;
import se.tronhage.webshop.repository.OrderRepo;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepo orderRepo;

    public List<Order> findAllConfirmedOrders(){
        return orderRepo.findByStatus(OrderStatus.CONFIRMED);
    }

    public List<Order> findAllShippedOrders(){
        return orderRepo.findByStatus(OrderStatus.SHIPPED);
    }

}
