package pl.margeb.check_please.bill.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import pl.margeb.check_please.group.domain.model.Group;

import java.time.LocalDate;
import java.util.UUID;
@Entity
@Table(name = "bills")
public class Bill {

    @Id
    private UUID id;
    private String name;
    private LocalDate date;

    @ManyToOne
    private Group group;

    public Bill() {
        this.id = UUID.randomUUID();
        this.date = date.now();
    }

    public Bill(String name) {
        this();
        this.name = name;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }


    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", group=" + group +
                '}';
    }
}
