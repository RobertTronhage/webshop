package se.tronhage.webshop.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime orderDate;
    private String status; // Exempel: "BEKRÄFTAD", "SKICKAD", "LEVERERAD"
    private int totalSum;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderLine> orderLines = new HashSet<>();


    public Orders() {}

    public Orders(LocalDateTime orderDate, String status, int totalSum, User user) {
        this.orderDate = orderDate;
        this.status = status;
        this.totalSum = totalSum;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(Set<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Orders orders = (Orders) o;

        if (totalSum != orders.totalSum) return false;
        if (!Objects.equals(id, orders.id)) return false;
        if (!Objects.equals(orderDate, orders.orderDate))
            return false;
        if (!Objects.equals(status, orders.status))
            return false;
        if (!Objects.equals(user, orders.user)) return false;
        return Objects.equals(orderLines, orders.orderLines);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + totalSum;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (orderLines != null ? orderLines.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", status='" + status + '\'' +
                ", totalSum=" + totalSum +
                ", user=" + user +
                ", orderLines=" + orderLines +
                '}';
    }
}