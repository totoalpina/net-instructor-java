package ro.netinstructor.models;

import ro.netinstructor.enums.UserRole;
import ro.netinstructor.validators.UniqueEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDto {
    @NotBlank
    @Email
    @UniqueEmail
    @Size(min = 6, max = 100)
    private String email;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    @NotBlank
    @Size(min = 1, max = 60)
    private String firstName;
    @NotBlank
    @Size(min = 1, max = 60)
    private String lastName;
    private UserRole userRole;

    private String verificationCode;

    private boolean enabled;

    public UserDto() {
    }

    public UserDto(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDto(String email, String password, String firstName, String lastName, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
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

