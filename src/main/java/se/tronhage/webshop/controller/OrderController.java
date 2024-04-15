package se.tronhage.webshop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.model.IModel;
import se.tronhage.webshop.entity.Order;
import se.tronhage.webshop.entity.OrderLine;
import se.tronhage.webshop.entity.Product;
import se.tronhage.webshop.entity.User;
import se.tronhage.webshop.enums.OrderStatus;
import se.tronhage.webshop.model.ShoppingBasket;
import se.tronhage.webshop.repository.OrderRepo;
import se.tronhage.webshop.services.EmailService;
import se.tronhage.webshop.services.OrderLineService;
import se.tronhage.webshop.services.OrderService;
import se.tronhage.webshop.services.ShoppingBasketManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class OrderController {

    private final OrderRepo ordersRepo;
    private final OrderService orderService;
    private final OrderLineService orderLineService;
    private final ShoppingBasketManager shoppingBasketManager;
    private final EmailService emailService;

    @Autowired
    public OrderController(OrderRepo ordersRepo, OrderService orderService,
                           OrderLineService orderLineService, ShoppingBasketManager shoppingBasketManager,
                           EmailService emailService) {
        this.ordersRepo = ordersRepo;
        this.orderService = orderService;
        this.orderLineService = orderLineService;
        this.shoppingBasketManager = shoppingBasketManager;
        this.emailService = emailService;
    }

    @PostMapping("/place-order")
    public String placeOrder(Model m, HttpSession session){
        ShoppingBasket shoppingBasket = (ShoppingBasket) session.getAttribute("shoppingbasket");
        if (shoppingBasket == null || shoppingBasket.getItems().isEmpty()) {
            m.addAttribute("error",
                    "Your cart is emtpy Please add items before placing an order.");
            System.out.println("null eller tom korg");
            return "shoppingbasket";
        }
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            m.addAttribute("error",
                    "You must be logged in to place an order.");
            return "login";
        }
        try {
            Order order = orderService.createOrderFromShoppingBasket(shoppingBasket, loggedInUser);
            session.setAttribute("currentorder", order);

            orderLineService.createOrderLineFromShoppingBasket(shoppingBasket, session);
            m.addAttribute("orderdetails", order.getOrderLines());
            // Skicka Orderemail
            emailService.sendSimpleMessage(loggedInUser.getEmail(), "Order Confirmation",
                    emailService.orderMessage(loggedInUser.getUsername()));
            return "redirect:/confirmation";
        } catch (Exception e) {
            m.addAttribute("error",
                    "An error occurred while placing the order: " + e.getMessage());
            System.out.println("catch i place order" + e.getMessage());
            return "shoppingbasket";
        }
    }

    @GetMapping("/orders")
    public String listOrders(@RequestParam(name = "type", required = false, defaultValue = "all") String type, Model m) {
        List<Order> orders = switch (type) {
            case "active" -> orderService.findAllConfirmedOrders();
            case "shipped" -> orderService.findAllShippedOrders();
            default -> ordersRepo.findAll();
        };
        m.addAttribute("orders", orders);
        return "orders";
    }

    @PostMapping("/orders")
    public String listOrdersPost(@RequestParam(name = "filterType", required = false, defaultValue = "all") String type) {
        // You can perform additional actions if needed
        // For example, redirect to another page after processing the POST request
        return "redirect:/orders?type=" + type;
    }

    @PostMapping("/orders/update-status")
    public String updateOrders(@RequestParam("orderId") Long orderId,
                               @RequestParam("status") String status,
                               Model m) {

        Optional<Order> orderOptional = ordersRepo.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus(OrderStatus.valueOf(status));

            orderService.updateOrder(order);

            m.addAttribute("order", order);
            return "redirect:/orders";
        } else {
            return "errorPage";
        }
    }

    @GetMapping("/confirmation")
    public String orderConfirmation(HttpSession session, Model m) {
        Order currentOrder = (Order) session.getAttribute("currentorder");

        if (currentOrder != null) {

            Set<OrderLine> orderLines = currentOrder.getOrderLines();

            m.addAttribute("orderLines", orderLines);

            m.addAttribute("orderId", currentOrder.getId());
            m.addAttribute("orderDate", currentOrder.getOrderDate());
            m.addAttribute("totalPrice", currentOrder.getTotalSum());

            m.addAttribute("message", "Order was successfully placed!");

            shoppingBasketManager.clearBasket();

            return "confirmation";
        } else {

            return "redirect:/shoppingbasket"; // Redirect to shopping basket page or handle appropriately
        }
    }

}

