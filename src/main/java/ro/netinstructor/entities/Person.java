package ro.netinstructor.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "persons")
public class Person {

    @Id
    @Column
    private long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String cnp;

    @Column
    private String address;

    @Column
    private String email;

    @Column
    private String phone;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;


    public Person() {
    }

    public Company getCompany() {
        return company;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(cnp, person.cnp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cnp);
    }
}
