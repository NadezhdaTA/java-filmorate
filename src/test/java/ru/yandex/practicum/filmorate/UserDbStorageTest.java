package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.db.UserDbStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({UserDbStorage.class})
class UserDbStorageTest extends TestData {
    private final UserDbStorage userStorage;

    @Test
    public void testGetUserById() {

        Optional<User> userOptional = userStorage.getUserById(1);

        assertThat(userOptional)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(getUser1());
    }

    @Test
    public void testGetUsersList() {
        Collection<User> users = userStorage.getUsersList();
        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(3);
    }

    @Test
    public void testAddFriend() {
        userStorage.addFriend(getUser1(), getUser2());
        assertThat(userStorage.getFriendsList(getUser1().getId()).size()).isEqualTo(1);
        assertThat(userStorage.getFriendsList(getUser2().getId()).size()).isEqualTo(0);
    }

    @Test
    public void testGetFriendsList() {
        userStorage.addFriend(getUser1(), getUser2());

        ArrayList<User> friends = new ArrayList<>();
        friends.add(getUser2());

        ArrayList<User> friends1 = new ArrayList<>(userStorage.getFriendsList(getUser1().getId()));

        assertThat(friends).isEqualTo(friends1);
    }

    @Test
    public void testDeleteFriend() {
        userStorage.addFriend(getUser1(), getUser2());
        assertThat(userStorage.getFriendsList(getUser1().getId()).size()).isEqualTo(1);

        userStorage.deleteFriend(getUser1(), getUser2());
        assertThat(userStorage.getFriendsList(getUser1().getId()).size()).isEqualTo(0);
    }
}
