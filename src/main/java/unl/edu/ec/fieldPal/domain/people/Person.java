package unl.edu.ec.fieldPal.domain.people;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class Person extends Organization{

    @NotNull @NotEmpty
    private String firstName;

    @NotNull @NotEmpty
    private String lastName;

    public Person() {
        setCreationDate(LocalDate.now());
    }

    public Person(Long id,
                  @NotNull @NotEmpty String firstName,
                  @NotNull @NotEmpty String lastName,
                  LocalDate creationDate,
                  IdentificationType identificationType,
                  String identificationNumber, String email
    ) {
        super(id, null, creationDate, identificationType, identificationNumber, email);
        //validateObligatoryField(firstName);
        //validateObligatoryField(lastName);
        setFirstName(firstName);
        setLastName(lastName);
        setName(getFullName());
    }

    @Deprecated
    private void validateObligatoryField(String text){
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotNull @NotEmpty String firstName) {
        this.firstName = firstName.trim().toUpperCase();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull @NotEmpty String lastName) {
        this.lastName = lastName.trim().toUpperCase();
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Person{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", ").append(super.toString());
        sb.append('}');
        return sb.toString();
    }
}