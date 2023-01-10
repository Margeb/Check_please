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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public UUID getPersonId() {
        return personId;
    }

    public void setPersonId(UUID personId) {
        this.personId = personId;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "BillOperation{" +
                "id=" + id +
                ", bill=" + bill +
                ", person=" + personId +
                ", deposit=" + deposit +
                ", cost=" + cost +
                '}';
    }
}
