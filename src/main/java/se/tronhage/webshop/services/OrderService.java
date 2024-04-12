package se.tronhage.webshop.services;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.tronhage.webshop.entity.Order;
import se.tronhage.webshop.entity.User;
import se.tronhage.webshop.enums.OrderStatus;
import se.tronhage.webshop.model.ShoppingBasket;
import se.tronhage.webshop.repository.OrderRepo;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepo orderRepo;
    private final ShoppingBasket shoppingBasket;
    private final ShoppingBasketManager shoppingBasketManager;
    private final UserService userService;

    @Autowired
    public OrderService(OrderRepo orderRepo, ShoppingBasket shoppingBasket, ShoppingBasketManager shoppingBasketManager, UserService userService) {
        this.orderRepo = orderRepo;
        this.shoppingBasket = shoppingBasket;
        this.shoppingBasketManager = shoppingBasketManager;
        this.userService = userService;
    }

    public Order createOrderFromShoppingBasket(ShoppingBasket shoppingBasket, User loggedInUser){
        Order order = new Order(LocalDateTime.now(),OrderStatus.CONFIRMED, shoppingBasketManager.calcTotalPrice(), loggedInUser);
        orderRepo.save(order);
        return order;
    }

    public List<Order> findAllConfirmedOrders(){
        return orderRepo.findByStatus(OrderStatus.CONFIRMED);
    }

    public List<Order> findAllShippedOrders(){
        return orderRepo.findByStatus(OrderStatus.SHIPPED);
    }


}
