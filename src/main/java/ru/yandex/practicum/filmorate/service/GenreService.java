package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.db.GenreDbStorage;

import java.util.Collection;

@Service
public class GenreService {
    private final GenreDbStorage storage;

    public GenreService(GenreDbStorage storage) {
        this.storage = storage;
    }

    public Collection<Genre> getAllGenres() {
        return storage.getAllGenres();
    }

    public Genre getGenreById(int id) {
        return storage.getGenreById(id)
                .orElseThrow(() -> new NotFoundException("Жанр с id = " + id + " не найден."));
    }

}
