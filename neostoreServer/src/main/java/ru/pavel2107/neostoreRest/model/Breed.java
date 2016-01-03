package ru.pavel2107.neostoreRest.model;

import javax.persistence.*;

/**
 * Created by lenovo on 27.12.2015.
 */
//
// породы хивотных. справочник один, моно описать как отдельную сущность
//
@Entity
@Table( name="breeds")
public class Breed {

    @Id
    @SequenceGenerator(name = "br_id_gen", sequenceName = "neostore_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "br_id_gen")
    private Integer id;

    @Column( name = "name", nullable = false)
    private String name;

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

    public Breed() {}

    public Breed(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Breed{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Breed breed = (Breed) o;

        if (id != null ? !id.equals(breed.id) : breed.id != null) return false;
        return !(name != null ? !name.equals(breed.name) : breed.name != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
