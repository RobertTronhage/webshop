package se.tronhage.webshop.Entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "shoppingbaskets")
public class ShoppingBasket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "shoppingBasket", fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "shoppingBasket", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BasketItem> basketItems = new HashSet<>();


    public ShoppingBasket() {

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<BasketItem> getBasketItems() {
        return basketItems;
    }

    public void setBasketItems(Set<BasketItem> basketItems) {
        this.basketItems = basketItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShoppingBasket that = (ShoppingBasket) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(user, that.user)) return false;
        return Objects.equals(basketItems, that.basketItems);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (basketItems != null ? basketItems.hashCode() : 0);
        return result;
    }
}
