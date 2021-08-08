package com.cierpich.blogio.users.domain.entity;


import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
public class User implements Persistable<UUID> {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    @Transient
    private Bio bio;
    private int reputation;

    public User(String firstName, String lastName, String email, Bio bio) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.bio = bio;
        this.reputation = 0;
    }

    public User(UUID id, String firstName, String lastName, String email, Bio bio, int reputation) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.bio = bio;
        this.reputation = reputation;
    }

    protected User(){
        this.bio = new Bio(null, null, null);
    }

    public void modifyReputationNonNegative(int value) {
        modifyReputation(value);
        forceNonNegativeReputation();
    }

    private void modifyReputation(int value) {
        reputation = reputation + value;
    }

    private void forceNonNegativeReputation() {
        reputation = Math.max(reputation, 0);
    }

    @Id
    @Column(name = "id", length = 16, unique = true, nullable = false)
    public UUID getId() {
        return id;
    }

    private boolean isNew = true;

    @Override
    @Transient
    public boolean isNew() {
        return isNew;
    }
    @PrePersist
    @PostLoad
    public void markNotNew(){
        this.isNew = false;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return bio.description;
    }

    public LocalDate getBirthDate() {
        return bio.birthDate;
    }

    public Bio.Gender getGender() {
        return bio.gender;
    }

    public int getReputation() {
        return reputation;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDescription(String description) {
        this.bio = bio.setDescription(description);
    }

    public void setBirthDate(LocalDate birthDate) {
        this.bio = bio.setBirthDate(birthDate);
    }

    public void setGender(Bio.Gender gender) {
        this.bio = bio.setGender(gender);
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (object == this) return true;
        if (object.getClass() == this.getClass()) {
            User user = (User) object;
            return Objects.equals(id, user.id);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class Builder {
        private UUID id = UUID.randomUUID();
        private String firstName;
        private String lastName;
        private String email;
        private String description = "";
        private LocalDate birthDate;
        private Bio.Gender gender = Bio.Gender.HIDDEN;
        private int reputation = 0;

        public Builder withId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder withGender(Bio.Gender gender) {
            this.gender = gender;
            return this;
        }

        public Builder withNonNegativeReputation(int reputation){
            this.reputation = Math.max(reputation, 0);
            return this;
        }

        public User build() {
            return new User(id, firstName, lastName, email, new Bio(birthDate, description, gender), reputation);
        }

    }

    public static class Bio {

        public final String description;
        public final LocalDate birthDate;
        public final Gender gender;

        public Bio(LocalDate birthDate, String description, Gender gender) {
            this.description = description;
            this.birthDate = birthDate;
            this.gender = gender;
        }

        public enum Gender {
            MALE, FEMALE, HIDDEN
        }

        public Bio setDescription(String description){
            return new Bio(this.birthDate, description, this.gender);
        }

        public Bio setBirthDate(LocalDate birthDate){
            return new Bio(birthDate, this.description, this.gender);
        }

        public Bio setGender(Gender gender){
            return new Bio(this.birthDate, this.description, gender);
        }

    }
}
