package se.tronhage.webshop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import se.tronhage.webshop.model.BasketItem;
import se.tronhage.webshop.model.ShoppingBasket;
import se.tronhage.webshop.services.ShoppingBasketManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ShoppingBasketManagerTest {

    private ShoppingBasketManager manager;

    @BeforeEach
    void setUp() {
        manager = new ShoppingBasketManager();
    }

    @Test
    void testAddItem_NewItem() {
        manager.addItem(1L, "Test Product", 100, 2);
        BasketItem expected = new BasketItem(1L, "Test Product", 2, 100);
        assertEquals(1, manager.getShoppingBasket().getItems().size());
        assertEquals(expected.getProductId(), manager.getShoppingBasket().getItems().get(0).getProductId());
        assertEquals(expected.getQuantity(), manager.getShoppingBasket().getItems().get(0).getQuantity());
    }

    @Test
    void testUpdateQuantity() {
        manager.addItem(1L, "Test Product", 100, 2);
        manager.updateQuantity(1L, 5);
        assertEquals(5, manager.getShoppingBasket().getItems().get(0).getQuantity());
    }

    @Test
    void testRemoveItem() {
        manager.addItem(1L, "Test Product", 100, 2);
        manager.removeItem(1L);
        assertTrue(manager.getShoppingBasket().getItems().isEmpty());
    }

    @Test
    void testCalcTotalPrice() {
        manager.addItem(1L, "Test Product", 100, 2);
        manager.addItem(2L, "Another Product", 200, 1);
        int totalPrice = manager.calcTotalPrice(manager.getShoppingBasket());
        assertEquals(400, totalPrice);
    }

    @Test
    void testClearBasket() {
        manager.addItem(1L, "Test Product", 100, 2);
        manager.clearBasket();
        assertTrue(manager.getShoppingBasket().getItems().isEmpty());
    }
}