package ru.yandex.practicum.filmorate.service;


import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.db.MpaDbStorage;

import java.util.Collection;

@Service
public class MpaService {
    private final MpaDbStorage dbStorage;

    public MpaService(MpaDbStorage dbStorage) {
        this.dbStorage = dbStorage;
    }

    public MpaRating getMpaRatingById(int id) {
        return dbStorage.getMpaRatingById(id)
                .orElseThrow(() -> new NotFoundException("Mpa_rating c id = " + id + " не найден."));
    }

    public Collection<MpaRating> getAllMpaRatings() {
        return dbStorage.getAllMpaRatings();
    }

}
