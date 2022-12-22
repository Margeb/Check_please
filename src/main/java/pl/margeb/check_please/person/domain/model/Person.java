package pl.margeb.check_please.person.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import pl.margeb.check_please.group.domain.model.Group;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "people")
public class Person {

    @Id
    private UUID id;
    private String name;
    private BigDecimal balance;

    @ManyToOne
    private Group group;

    public Person() {
        this.id = UUID.randomUUID();
        this.balance = BigDecimal.ZERO;
    }

    public Person(String name) {
        this();
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
