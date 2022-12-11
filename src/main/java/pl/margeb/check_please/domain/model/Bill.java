package pl.margeb.check_please.domain.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public class Bill {

    private UUID id;
    private String name;
    private LocalDate date; //date.now() - current date

    private Map<Person, Float> howMuchEveryPersonPaid;

    public Bill() {
    }

    public Bill(UUID id, String name, LocalDate date) {
        this.id = id;
        this.name = name;
        this.date = date;
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

    public Map<Person, Float> getHowMuchEveryPersonPaid() {
        return howMuchEveryPersonPaid;
    }

    public void setHowMuchEveryPersonPaid(Map<Person, Float> howMuchEveryPersonPaid) {
        this.howMuchEveryPersonPaid = howMuchEveryPersonPaid;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", howMuchEveryPersonPaid=" + howMuchEveryPersonPaid +
                '}';
    }
}
