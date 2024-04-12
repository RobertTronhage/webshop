package se.tronhage.webshop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.tronhage.webshop.entity.Order;
import se.tronhage.webshop.entity.OrderLine;
import se.tronhage.webshop.entity.User;
import se.tronhage.webshop.model.ShoppingBasket;
import se.tronhage.webshop.repository.OrderRepo;
import se.tronhage.webshop.services.OrderLineService;
import se.tronhage.webshop.services.OrderService;

import java.util.List;

@Controller
public class OrderController {

    private final OrderRepo ordersRepo;
    private final OrderService orderService;
    private final OrderLineService orderLineService;

    @Autowired
    public OrderController(OrderRepo ordersRepo, OrderService orderService, OrderLineService orderLineService) {
        this.ordersRepo = ordersRepo;
        this.orderService = orderService;
        this.orderLineService = orderLineService;
    }

    @PostMapping("/place-order")
    public String placeOrder(Model m, HttpSession session){

        ShoppingBasket shoppingBasket = (ShoppingBasket) session.getAttribute("shoppingBasket");

        User loggedInUser = (User) session.getAttribute("loggedInUser");

        Order order = orderService.createOrderFromShoppingBasket(shoppingBasket, loggedInUser);
        session.setAttribute("currentorder", order);
        orderLineService.createOrderLineFromShoppingBasket(shoppingBasket,session);

        m.addAttribute("orderdetails",order);

        return "redirect:/confirmation";
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

}

