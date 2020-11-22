package pl.sda.finalapp.app.users;

public class UserRegistrationDTO {

    private String firstName;
    private String lastName;
    private String eMail;
    private String password;
    private String city;
    private String country;
    private String zipCode;
    private String street;
    private String birthDate;
    private String pesel;
    private String phone;
    private boolean preferEmails;

    public UserRegistrationDTO() {
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPreferEmails(boolean preferEmails) {
        this.preferEmails = preferEmails;
    }

    public UserRegistrationDTO(String firstName, String lastName, String eMail, String password, String city, String country, String zipCode, String street, String birthDate, String pesel, String phone, boolean preferEmails) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.password = password;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
        this.street = street;
        this.birthDate = birthDate;
        this.pesel = pesel;
        this.phone = phone;
        this.preferEmails = preferEmails;
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

    public String getPassword() {
        return password;
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