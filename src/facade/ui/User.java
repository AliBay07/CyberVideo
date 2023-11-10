package facade.ui;

import java.util.Date;

public class User {
    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    public User(String firstName, String lastName, Date dateOfBirth)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public User(){}

    // Getter pour le prénom (FirstName)
    public String getFirstName() {
        return firstName;
    }

    // Setter pour le prénom (FirstName)
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter pour le nom de famille (LastName)
    public String getLastName() {
        return lastName;
    }

    // Setter pour le nom de famille (LastName)
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Getter pour la date de naissance (DateOfBirth)
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    // Setter pour la date de naissance (DateOfBirth)
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
