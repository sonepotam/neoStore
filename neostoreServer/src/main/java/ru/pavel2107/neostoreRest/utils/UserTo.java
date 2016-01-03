package ru.pavel2107.neostoreRest.utils;

/**
 * Created by lenovo on 17.12.2015.
 */
//
// DTO - для пользователя
//
public class UserTo {
    protected int id;

    protected String name;

    protected String password;

    public UserTo() {}

    public UserTo(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = (id == null ? 0 : id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "UserTo{" +
               "id=" + id +
               ", name='" + name + '\'' +
                '}';
    }

}

