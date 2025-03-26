package ru.yandex.practicum.filmorate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
public class Friends {
    private int userId;
    private int friendId;
    @Enumerated(EnumType.STRING)
    private Friendship friendship;

    public Friends(int userId, int friendId, Friendship friendship) {
        this.userId = userId;
        this.friendId = friendId;
        this.friendship = friendship;

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Friends that = (Friends) o;
        return userId == that.userId && friendId == that.friendId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, friendId);
    }

}
