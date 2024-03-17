package se.tronhage.webshop.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "shopping_basket_id")
    private ShoppingBasket shoppingBasket;

}
