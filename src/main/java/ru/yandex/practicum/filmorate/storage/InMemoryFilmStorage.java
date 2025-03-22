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
        Film oldFilm = films.get(newFilm.getId());

        oldFilm.setName(newFilm.getName());
        oldFilm.setDescription(newFilm.getDescription());
        oldFilm.setReleaseDate(newFilm.getReleaseDate());
        oldFilm.setDuration(newFilm.getDuration());

        films.put(oldFilm.getId(), newFilm);
        return oldFilm;
    }

    @Override
    public Map<Integer, Film> getFilms() {
        return films;
    }

    @Override
    public Optional<Film> getFilmById(int id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public void addLikes(int filmId, int userId) {
        filmLikes.computeIfAbsent(filmId, f -> new HashSet<>()).add(userId);
    }

    @Override
    public void deleteLikes(int filmId, int userId) {
        filmLikes.get(filmId).remove(userId);
        if (filmLikes.get(filmId).isEmpty()) {
            filmLikes.remove(filmId);
        }
    }

    public Map<Integer, Set<Integer>> getPopularFilms(int count) {
        return filmLikes;

    }
}
