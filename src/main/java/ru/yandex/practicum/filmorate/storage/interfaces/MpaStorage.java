package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.Collection;
import java.util.Optional;

public interface MpaStorage {
    Optional<MpaRating> getMpaRatingById(int id);

    Collection<MpaRating> getAllMpaRatings();
}
