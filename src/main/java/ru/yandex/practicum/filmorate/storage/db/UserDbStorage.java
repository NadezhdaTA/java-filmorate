package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;
import ru.yandex.practicum.filmorate.storage.mappers.UserRowMapper;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Qualifier("UserDbStorage")
public class UserDbStorage extends UserRowMapper implements UserStorage {
    private final JdbcTemplate jdbc;
    private final UserRowMapper mapper = new UserRowMapper();


    @Override
    public User createUser(User user) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO USERS (EMAIL, LOGIN, USER_NAME, BIRTHDAY) " +
                            "VALUES (?, ?, ?, ?)", new String[]{"user_id"});
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getName());
            ps.setString(4, user.getBirthday().toString());
            return ps;
        }, keyHolder);
        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public User updateUser(User newUser) {
        jdbc.update("UPDATE USERS SET EMAIL = ?, LOGIN = ?, " +
                        "USER_NAME = ?, BIRTHDAY = ? where user_id = ?",
                newUser.getEmail(), newUser.getLogin(), newUser.getName(),
                newUser.getBirthday().toString(), newUser.getId());

        return newUser;
    }

    @Override
    public Collection<User> getUsersList() {
        return jdbc.query("SELECT * FROM USERS", new UserRowMapper());
    }

    @Override
    public Optional<User> getUserById(int user_id) throws DataAccessException{
        int id = jdbc.queryForObject("SELECT COUNT(login) FROM users WHERE user_id = ?",
                Integer.class, user_id);

        if (id == 0) {
            return Optional.empty();
        }

        return Optional.of(jdbc.queryForObject("SELECT * FROM USERS WHERE USER_ID = ?",
                    new UserRowMapper(), user_id));
    }

    @Override
    public void addFriend(User user, User friend) {
        jdbc.update("INSERT INTO friends (user_id, friend_id) VALUES (?, ?)",
                user.getId(), friend.getId());
    }

    @Override
    public Collection<User> getFriendsList(int id) {
        return jdbc.query("SELECT u.user_id, user_name, email, login, birthday " +
                "FROM users u JOIN friends f on u.user_id = f.friend_id WHERE f.user_id = ?",
                new UserRowMapper(), id);
    }

    @Override
    public void deleteFriend(User user, User friend) {
        jdbc.update("DELETE FROM friends WHERE user_id = ? AND friend_id = ?",
                user.getId(), friend.getId());
    }
}
