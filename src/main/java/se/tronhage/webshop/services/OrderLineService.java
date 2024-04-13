package se.tronhage.webshop.services;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.tronhage.webshop.entity.Order;
import se.tronhage.webshop.entity.OrderLine;
import se.tronhage.webshop.entity.Product;
import se.tronhage.webshop.model.BasketItem;
import se.tronhage.webshop.model.ShoppingBasket;

@Service
public class OrderLineService {

    private final ProductService productService;

    @Autowired
    public OrderLineService(ProductService productService) {
        this.productService = productService;
    }

    public void createOrderLineFromShoppingBasket(ShoppingBasket shoppingBasket,
                                                  HttpSession session) {
        if (shoppingBasket == null) {
            throw new IllegalArgumentException(
                    "No shopping cart available to create order lines.");
        }
        Order currentOrder = (Order) session.getAttribute("currentorder");
        if (currentOrder == null) {
            throw new IllegalStateException(
                    "No current order in session.");
        }
        for (BasketItem item : shoppingBasket.getItems()) {
            Product product = productService.getProductById(item.getProductId());

            OrderLine orderLine = new OrderLine(
                    item.getQuantity(),
                    item.getUnitPrice(),
                    currentOrder,
                    product
            );
            currentOrder.addOrderLine(orderLine);
        }
    }
}
