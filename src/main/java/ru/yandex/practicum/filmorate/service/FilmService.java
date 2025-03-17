package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
public class FilmService {
    private final FilmStorage filmStorage = new InMemoryFilmStorage();

    public Film createFilm(Film film) throws ValidationException {
        return filmStorage.createFilm(film);
    }

    public Film getFilmById(int id) throws NotFoundException {
        return  filmStorage.getFilmById(id)
                .orElseThrow(() -> new NotFoundException("Фильм с таким id = " + id + " не найден."));
    }

    public Film updateFilm(Film newFilm) throws DuplicatedDataException, NotFoundException, ValidationException {
        Film oldFilm = getFilmById(newFilm.getId());

        filmStorage.getFilmsList().stream()
                .filter(film -> !film.getName().equals(newFilm.getName()))
                .findFirst()
                .orElseThrow(() -> new DuplicatedDataException("Фильм с названием \"" + newFilm.getName() + "\" уже существует."));

        oldFilm.setName(newFilm.getName());
        oldFilm.setDescription(newFilm.getDescription());
        oldFilm.setReleaseDate(newFilm.getReleaseDate());
        oldFilm.setDuration(newFilm.getDuration());

        return filmStorage.updateFilm(oldFilm);
    }

    public Collection<Film> getFilmsList() {
        return filmStorage.getFilmsList();
    }

    public void addLikes(int filmId, int userId) throws NotFoundException {
        Set<Integer> userIds = filmStorage.getLikesCount(filmId);
        userIds.add(userId);
        getFilmById(filmId).setLikes(userIds.size());
        filmStorage.addLikes(filmId, userIds);
    }

    public void deleteLikes(int filmId, int userId) throws NotFoundException {
        Set<Integer> userIds = filmStorage.getLikesCount(filmId);
        userIds.remove(userId);
        getFilmById(filmId).setLikes(userIds.size());
        filmStorage.addLikes(filmId,userIds);
    }

    public Collection<Film> getPopularFilms(int count) {
        return filmStorage.getFilmsList().stream()
                .sorted(Comparator.comparing(Film::getLikes))
                .limit(count)
                .collect(Collectors.toList());
    }

}
