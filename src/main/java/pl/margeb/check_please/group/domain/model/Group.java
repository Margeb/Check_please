package pl.margeb.check_please.group.domain.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import pl.margeb.check_please.bill.domain.model.Bill;
import pl.margeb.check_please.person.domain.model.Person;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@Entity
@Data
@Table(name = "groups")
public class Group {

    @Id
    private UUID id;
    @NotBlank(message = "{check.validation.name.NotBlank.message}")
    @Size(min = 3, max = 255)
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "group")
    private Set<Bill> bills;

    @OneToMany(mappedBy = "group")
    private Set<Person> people;


    public Set<Bill> getBills() {
        return Collections.unmodifiableSet(bills);
    }

    public Set<Person> getPeople() {
        return Collections.unmodifiableSet(people);
    }

    public Group() {
        this.id = UUID.randomUUID();
    }

    public Group(String name) {
        this();
        this.name = name;
    }

    public Group addBill(Bill bill)
    {
        if(bills == null)
        {
            bills = new HashSet<>();
        }

        bill.setGroup(this);
        bills.add(bill);

        return this;
    }

    public void addPerson(Person person) {

        if(people == null){
            people = new HashSet<>();
        }
        person.setGroup(this);

        people.add(person);
    }
}
