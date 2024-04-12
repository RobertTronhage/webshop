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

private final ShoppingBasket shoppingBasket;
private final ProductService productService;


    @Autowired
    public OrderLineService(ShoppingBasket shoppingBasket, ProductService productService) {
        this.shoppingBasket = shoppingBasket;
        this.productService = productService;
    }

    public void createOrderLineFromShoppingBasket(ShoppingBasket shoppingBasket,
                                                  HttpSession session){

        Order currentOrder = (Order) session.getAttribute("currentorder");



        for (BasketItem items : shoppingBasket.getItems()){
            Product product = productService.getProductById(items.getProductId());

            OrderLine orderLine = new OrderLine(
                    items.getQuantity(),
                    items.getUnitPrice(),
                    currentOrder,
                    product
            );
            currentOrder.addOrderLine(orderLine);
        }
    }
}
