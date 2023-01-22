package pl.margeb.checkplease.group.domain.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import pl.margeb.checkplease.bill.domain.model.Bill;
import pl.margeb.checkplease.person.domain.model.Person;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@Entity
@Getter
@Setter
@Table(name = "groups")
public class Group {

    @Id
    private UUID id;

    @NotBlank(message = "{check.validation.name.NotBlank.message}")
    @Size(min = 3, max = 255)
    @Column(unique = true)
    private String name;

    @OneToMany
    private Set<Bill> bills;

    @OneToMany
    private Set<Person> people;

    public Group() {
        this.id = UUID.randomUUID();
    }

    public Group(String name) {
        this();
        this.name = name;
    }

    public Set<Bill> getBills() {
        return Collections.unmodifiableSet(bills);
    }

    public Set<Person> getPeople() {
        return Collections.unmodifiableSet(people);
    }

    public Group addBill(Bill bill)
    {
        if(bills == null) {
            bills = new HashSet<>();
        }

        bill.setGroupId(this.getId());
        bills.add(bill);

        return this;
    }

    public void addPerson(Person person) {

        if(people == null){
            people = new HashSet<>();
        }

        person.setGroupId(this.getId());
        people.add(person);
    }

    public void deletePerson(Person person){
        people.remove(person);
    }

    public void deleteBill(Bill bill){
        bills.remove(bill);
    }
}
