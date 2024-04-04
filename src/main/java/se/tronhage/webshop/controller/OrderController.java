package se.tronhage.webshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.tronhage.webshop.entity.Order;
import se.tronhage.webshop.repository.OrderRepo;
import se.tronhage.webshop.services.OrderService;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    OrderRepo ordersRepo;

    @Autowired
    OrderService orderService;


    @GetMapping("/orders")
    public String listOrders(@RequestParam(name = "type", required = false, defaultValue = "all") String type, Model m) {
        List<Order> orders;
        switch (type) {
            case "active":
                orders = orderService.findAllConfirmedOrders();
                break;
            case "shipped":
                orders = orderService.findAllShippedOrders();
                break;
            case "all":
            default:
                orders = ordersRepo.findAll();
        }
        m.addAttribute("orders", orders);
        return "orders";
    }

    @PostMapping("/orders")
    public String listOrdersPost(@RequestParam(name = "filterType", required = false, defaultValue = "all") String type) {
        // You can perform additional actions if needed
        // For example, redirect to another page after processing the POST request
        return "redirect:/orders?type=" + type;
    }

//    @GetMapping("/activeorders")
//    public String listActiveOrders(Model m){
//        List<Order> orders = orderService.findAllConfirmedOrders();
//        m.addAttribute("orders",orders);
//        return "activeorders";
//    }
//
//    @GetMapping("/shippedOrders")
//    public String listShippedOrders(Model m){
//        List<Order> orders = orderService.findAllShippedOrders();
//        m.addAttribute("orders",orders);
//        return "shippedOrders";
//    }
//
//    @GetMapping("/allOrders")
//    public String listAllOrders(Model m){
//        List<Order> allOrders = ordersRepo.findAll();
//        m.addAttribute("orders",allOrders);
//        return "allorders";
//    }

}
