package ro.netinstructor.entities;


import org.springframework.lang.NonNull;
import ro.netinstructor.enums.UserRole;

import javax.persistence.*;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "unique_email", columnNames = {"email"})
})
public class User {

    @Id
    @Column
    @GeneratedValue
    private Long id;

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    private String password;

    @Column(name = "first_name", length = 60)
    private String firstName;

    @Column(name = "last_name", length = 60)
    private String lastName;

    @Column(length = 10, name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public User() {
    }

    public User(@NonNull String email, @NonNull String password, String firstName, String lastName, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
    }

    public User(Long id, @NonNull String email, @NonNull String password, String firstName, String lastName, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
