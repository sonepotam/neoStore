package ru.pavel2107.neostoreRest.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by lenovo on 30.11.2015.
 *

 */

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "username", name = "us_unique_name")})
public class User  {

    @Id
    @SequenceGenerator(name = "user_id_gen", sequenceName = "neostore_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_gen")
    private Integer id;

    @Column( name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column( name = "lastModified")
    private LocalDateTime lastModified;

    @Column( name = "attempts")
    private Integer attempts;


    public User() {}

    public User( Integer id, String username, String password, LocalDateTime lastModified, Integer attempts) {
        this.id       = id;
        this.username = username;
        this.password = password;
        this.lastModified = lastModified;
        this.attempts = attempts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", lastModified=" + lastModified +
                ", attempts=" + attempts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        if (!username.equals(user.username)) return false;
        if (!password.equals(user.password)) return false;
        if (lastModified != null ? !lastModified.equals(user.lastModified) : user.lastModified != null) return false;
        return !(attempts != null ? !attempts.equals(user.attempts) : user.attempts != null);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (lastModified != null ? lastModified.hashCode() : 0);
        result = 31 * result + (attempts != null ? attempts.hashCode() : 0);
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }
}
