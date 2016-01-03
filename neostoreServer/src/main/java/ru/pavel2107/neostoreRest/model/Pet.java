package ru.pavel2107.neostoreRest.model;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by lenovo on 30.11.2015.
 */
//
// сущность для описания животных
//
@Entity
@Table( name="pets")
public class Pet  {

    @Id
    @SequenceGenerator(name = "pet_id_gen", sequenceName = "neostore_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pet_id_gen")
    private Integer id;

    @Column( name = "name", nullable = false)
    private String name;

    @Column( name ="breed_id")
    private Integer breed;

    @Column( name="birthDate")
    private LocalDate birthDate;

    @Column( name = "sex")
    private String sex;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    User user;

    public Pet() {}

    public Pet(String name, Integer breed, LocalDate birthDate, String sex, User user) {
        this.name = name;
        this.breed = breed;
        this.birthDate = birthDate;
        this.sex = sex;
        this.user = user;
    }

    public Pet( Integer id, String name, Integer breed, LocalDate birthDate, String sex, User user) {
        this( name, breed, birthDate, sex, user);
        this.id = id;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", username='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", birthDate=" + birthDate +
                ", sex='" + sex + '\'' +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pet pet = (Pet) o;

        if (!id.equals(pet.id)) return false;
        if (!name.equals(pet.name)) return false;
        if (breed != null ? !breed.equals(pet.breed) : pet.breed != null) return false;
        if (birthDate != null ? !birthDate.equals(pet.birthDate) : pet.birthDate != null) return false;
        if (sex != null ? !sex.equals(pet.sex) : pet.sex != null) return false;
        return user.equals(pet.user);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (breed != null ? breed.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + user.hashCode();
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBreed() {
        return breed;
    }
    public void setBreed(Integer breed) {
        this.breed = breed;
    }


    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
