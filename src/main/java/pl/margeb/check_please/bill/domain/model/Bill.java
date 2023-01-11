package pl.margeb.check_please.bill.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import pl.margeb.check_please.group.domain.model.Group;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@Entity
@Table(name = "bills")
@Getter
@Setter
@ToString
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

    public void addBillOperation(BillOperation billOperation) {
        if(billOperations == null)
        {
            billOperations = new HashSet<>();
        }

        billOperations.add(billOperation);
    }
}
