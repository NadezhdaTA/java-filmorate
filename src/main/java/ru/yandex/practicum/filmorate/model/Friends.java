package ru.yandex.practicum.filmorate.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"userId"})
public class Friends {
    private int userId;
    private int friendId;
    @Enumerated(EnumType.STRING)
    private FriendsStatus status;

    public Friends(int userId, int friendId, FriendsStatus status) {
        this.userId = userId;
        this.friendId = friendId;
        this.status = status;

    }

}
