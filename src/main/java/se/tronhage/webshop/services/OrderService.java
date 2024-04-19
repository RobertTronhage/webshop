package se.tronhage.webshop.services;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.tronhage.webshop.entity.Order;
import se.tronhage.webshop.entity.Product;
import se.tronhage.webshop.entity.User;
import se.tronhage.webshop.enums.OrderStatus;
import se.tronhage.webshop.exceptions.ProductNotFoundException;
import se.tronhage.webshop.model.ShoppingBasket;
import se.tronhage.webshop.repository.OrderRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepo orderRepo;
    private final ShoppingBasketManager shoppingBasketManager;

    @Autowired
    public OrderService(OrderRepo orderRepo, ShoppingBasketManager shoppingBasketManager) {
        this.orderRepo = orderRepo;
        this.shoppingBasketManager = shoppingBasketManager;

    }

    public Order createOrderFromShoppingBasket(ShoppingBasket shoppingBasket, User loggedInUser) {

        if (shoppingBasket == null) {
            throw new IllegalArgumentException("Shopping cart cannot be null.");
        }
        if (loggedInUser == null) {
            throw new IllegalArgumentException("User must be logged in to place an order.");
        }
        int totalPrice = shoppingBasketManager.calcTotalPrice(shoppingBasket);
        Order order = new Order(LocalDateTime.now(), OrderStatus.CONFIRMED,
                totalPrice, loggedInUser);
        orderRepo.save(order);
        return order;
    }

    @Transactional
    public void updateOrder(Order updateOrder) {
        Optional<Order> optionalOrder = orderRepo.findById(updateOrder.getId());

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            order.setStatus(updateOrder.getStatus());

            orderRepo.save(order);
        } else {
            throw new ProductNotFoundException();
        }
    }

    public List<Order> findAllConfirmedOrders(){
        return orderRepo.findByStatus(OrderStatus.CONFIRMED);
    }

    public List<Order> findAllShippedOrders(){
        return orderRepo.findByStatus(OrderStatus.SHIPPED);
    }

    public List<Order> findOrdersByUser(User loggedInUser) {
        return orderRepo.findByUser(loggedInUser);
    }
}
