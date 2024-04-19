package se.tronhage.webshop.services;

import org.springframework.stereotype.Service;
import se.tronhage.webshop.entity.OrderLine;
import se.tronhage.webshop.entity.Order;
import se.tronhage.webshop.entity.Product;
import se.tronhage.webshop.entity.User;
import se.tronhage.webshop.enums.OrderStatus;
import se.tronhage.webshop.model.BasketItem;
import se.tronhage.webshop.model.ShoppingBasket;
import se.tronhage.webshop.repository.OrderRepo;
import se.tronhage.webshop.repository.ProductRepo;

import java.time.LocalDateTime;

@Service
public class CheckoutService {

    private final OrderRepo ordersRepo;
    private final ProductRepo productRepo;

    public CheckoutService(OrderRepo ordersRepo, ProductRepo productRepo) {
        this.ordersRepo = ordersRepo;
        this.productRepo = productRepo;
    }

    public Order completePurchase(ShoppingBasket basket, User user) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.CONFIRMED);

        int totalSum = 0;
        for (BasketItem item : basket.getItems()) {
            Product product = productRepo.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProductId()));

            OrderLine orderLine = new OrderLine();
            orderLine.setProduct(product);
            orderLine.setQuantity(item.getQuantity());
            orderLine.setUnitPrice(item.getUnitPrice());

            order.addOrderLine(orderLine);
            orderLine.setOrders(order); // Koppla OrderLine till Order

            totalSum += item.getUnitPrice() * item.getQuantity();
        }

        order.setTotalSum(totalSum);
        ordersRepo.save(order);

        basket.clear();

        return order;
    }
}
