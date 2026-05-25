package com.solvd.airline.entity;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Maps the {@code passengers} table.
 *
 * <pre>
 * passenger_id   BIGINT       PK
 * first_name     VARCHAR(60)
 * last_name      VARCHAR(60)
 * email          VARCHAR(120) UNIQUE
 * phone          VARCHAR(30)  nullable
 * date_of_birth  DATE
 * created_at     TIMESTAMP    default CURRENT_TIMESTAMP
 * </pre>
 */
public class Passenger extends BaseEntity {

    private String    firstName;
    private String    lastName;
    private String    email;
    private String    phone;       // nullable
    private LocalDate dateOfBirth;
    private Instant   createdAt;

    public Passenger() { }

    public String getFirstName()           { return firstName; }
    public void   setFirstName(String n)   { this.firstName = n; }

    public String getLastName()            { return lastName; }
    public void   setLastName(String n)    { this.lastName = n; }

    public String getEmail()               { return email; }
    public void   setEmail(String email)   { this.email = email; }

    public String getPhone()               { return phone; }
    public void   setPhone(String phone)   { this.phone = phone; }

    public LocalDate getDateOfBirth()      { return dateOfBirth; }
    public void setDateOfBirth(LocalDate d){ this.dateOfBirth = d; }

    public Instant getCreatedAt()          { return createdAt; }
    public void    setCreatedAt(Instant t) { this.createdAt = t; }

    @Override
    public String toString() {
        return "Passenger{" + firstName + " " + lastName + " <" + email + ">}";
    }
}
