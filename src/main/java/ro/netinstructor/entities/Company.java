package ro.netinstructor.entities;

import ro.netinstructor.utility.Utilities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Company {

    @Id
    @Column
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "cif")
    private String cif;

//    @Column(name = "email")
//    private String email;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY
            , cascade = CascadeType.ALL)
    private Set<User> users;

    public Company() {
    }

    public Company(Long id, String name, String address, String cif) {
        this.id = Utilities.createID();
        this.name = name;
        this.address = address;
        this.cif = cif;
        this.users = new LinkedHashSet<>();
    }

    public Company(String name, String address, String cif) {
        this.id = Utilities.createID();
        this.name = name;
        this.address = address;
        this.cif = cif;
        this.users = new LinkedHashSet<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return cif.equals(company.cif);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cif);
    }

}
