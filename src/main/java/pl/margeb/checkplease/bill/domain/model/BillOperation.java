package pl.margeb.checkplease.bill.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "bill_operations")
@Getter
@Setter
public class BillOperation {

    @Id
    private UUID id;

    @NotNull
    private UUID billId;

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

    public BillOperation(UUID billId, UUID personId, BigDecimal deposit, BigDecimal cost) {
        this();
        this.billId = billId;
        this.personId = personId;
        this.deposit = deposit;
        this.cost = cost;
    }
}
