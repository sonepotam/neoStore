package ru.pavel2107.neostoreRest.utils;

import ru.pavel2107.neostoreRest.model.User;

/**
 * Created by lenovo on 17.12.2015.
 */
//
// конструируем DTO объект для пользователя
//
public class UserUtil {
    public static User createFromTo(UserTo newUser) {
        return new User(null, newUser.getName(), newUser.getPassword(), null, 0);
    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getUsername(), user.getPassword());
    }
}
