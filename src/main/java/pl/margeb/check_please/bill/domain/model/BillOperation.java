package pl.margeb.check_please.bill.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "bill_operations")
@Data
public class BillOperation {

    @Id
    private UUID id;

    @ManyToOne
    private Bill bill;

    @NotNull
    private UUID personId;

    @NumberFormat
    @NotNull(message = "Deposit field can't be empty")
    private BigDecimal deposit;

    @NumberFormat
    @NotNull(message = "Cost field can't be empty")
    private BigDecimal cost;

    public BillOperation() {
        this.id = UUID.randomUUID();
    }

    public BillOperation(Bill bill, UUID personId, BigDecimal deposit, BigDecimal cost) {
        this();
        this.bill = bill;
        this.personId = personId;
        this.deposit = deposit;
        this.cost = cost;
    }
}
