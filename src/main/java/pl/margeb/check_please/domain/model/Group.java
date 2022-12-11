package pl.margeb.check_please.domain.model;

import pl.margeb.check_please.domain.model.Bill;

import java.util.List;
import java.util.UUID;

public class Group {

    private UUID id;
    private String name;
    private List<Bill> bills;



    public Group() {
    }

    public Group(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bills=" + bills +
                '}';
    }
}
