package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private int nextFilmId = 1;

    private final Map<Integer, Film> films = new HashMap<>();
    private Map<Integer, Set<Integer>> filmLikes = new HashMap<>();

    @Override
    public Film createFilm(Film film) {
        film.setId(nextFilmId++);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film newFilm) {
        films.put(newFilm.getId(), newFilm);
        return newFilm;
    }

    @Override
    public Collection<Film> getFilmsList() {
        return films.values();
    }

    @Override
    public Optional<Film> getFilmById(int id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public void addLikes(int filmId, Set<Integer> userIds) {
        filmLikes.put(filmId, userIds);

    }

    public Set<Integer> getLikesCount(int filmId) {
        return filmLikes.get(filmId);
    }
}
