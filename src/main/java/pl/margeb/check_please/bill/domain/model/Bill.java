package pl.margeb.check_please.bill.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import pl.margeb.check_please.group.domain.model.Group;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@Entity
@Table(name = "bills")
public class Bill {

    @Id
    private UUID id;
    @NotBlank(message = "{check.validation.name.NotBlank.message}")
    @Size(min = 3, max = 255)
    @Column(unique = true)
    private String name;
    @DateTimeFormat
    private LocalDate date;

    @OneToMany(mappedBy = "bill")
    private Set<BillOperation> billOperations;

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

    public Set<BillOperation> getBillOperations() {
        return billOperations;
    }

    public void setBillOperations(Set<BillOperation> billOperations) {
        this.billOperations = billOperations;
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

    public void addBillOperation(BillOperation billOperation) {
        if(billOperations == null)
        {
            billOperations = new HashSet<>();
        }

        billOperations.add(billOperation);
    }
}
