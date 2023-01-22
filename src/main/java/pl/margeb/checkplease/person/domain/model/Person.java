package pl.margeb.checkplease.person.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;
import pl.margeb.checkplease.bill.domain.model.BillOperation;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "people")
public class Person {

    @Id
    private UUID id;
    @NotBlank(message = "{check.validation.name.NotBlank.message}")
    @Size(min = 3, max = 255)
    @Column(unique = true)
    private String name;
    @NumberFormat
    private BigDecimal balance;

    @OneToMany
    private Set<BillOperation> billOperations;

    private UUID groupId;

    public Person() {
        this.id = UUID.randomUUID();
        this.balance = BigDecimal.ZERO;
    }

    public Person(String name) {
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
