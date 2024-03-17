package se.tronhage.webshop.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "shoppingbaskets")
public class ShoppingBasket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "shoppingBasket")
    private User user;

    @OneToMany(mappedBy = "shoppingBasket", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BasketItem> items;



    public ShoppingBasket() {

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public void setItems(List<BasketItem> items) {
        this.items = items;
    }
}
