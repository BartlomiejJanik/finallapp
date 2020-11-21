package pl.sda.finalapp.app.users;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String firstName;
    private String lastName;
    private String eMail;
    private String passwordHash;
    private String city;
    private String country;
    private String zipCode;
    private String street;
    private String birthDate;
    private String pesel;
    private String phone;
    private boolean preferEmails;
    @ManyToMany
    @JoinTable(name = "Users_roles")
    private List<Role> roles;

    public User(String firstName, String lastName, String eMail, String passwordHash, String city, String country, String zipCode, String street, String birthDate, String pesel, String phone, boolean preferEmails) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.passwordHash = passwordHash;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
        this.street = street;
        this.birthDate = birthDate;
        this.pesel = pesel;
        this.phone = phone;
        this.preferEmails = preferEmails;
    }

    public User() {
    }

    public void addRole(Role role) {
        if (roles == null) {
            roles = new ArrayList<>();
            roles.add(role);
        } else if (!roles.contains(role)) {
            roles.add(role);

        }

    }

    public static User applyDTO(UserRegistrationDTO dto, String passwordHash) {
        User user = new User();
        user.firstName = dto.getFirstName();
        user.lastName = dto.getLastName();
        user.eMail = dto.geteMail();
        user.passwordHash = passwordHash;
        user.city = dto.getCity();
        user.country = dto.getCountry();
        user.zipCode = dto.getZipCode();
        user.street = dto.getStreet();
        user.birthDate = dto.getBirthDate();
        user.pesel = dto.getPesel();
        user.phone = dto.getPhone();
        user.preferEmails = dto.isPreferEmails();
        return user;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String geteMail() {
        return eMail;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getStreet() {
        return street;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getPesel() {
        return pesel;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isPreferEmails() {
        return preferEmails;
    }
}