package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;

@Service
@RequiredArgsConstructor
@Validated
public class FilmService {
    private final FilmStorage filmStorage = new InMemoryFilmStorage();

    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    public Film getFilmById(int id) throws NotFoundException {
        return  filmStorage.getFilmById(id)
                .orElseThrow(() -> new NotFoundException("Фильм с таким id = " + id + " не найден."));
    }

    public Film updateFilm(Film newFilm) throws DuplicatedDataException, NotFoundException {
        getFilmById(newFilm.getId());

        filmStorage.getFilms().values().stream()
                .filter(film -> !film.getName().equals(newFilm.getName()))
                .findFirst()
                .orElseThrow(() -> new DuplicatedDataException("Фильм с названием \"" + newFilm.getName() + "\" уже существует."));

        return filmStorage.updateFilm(newFilm);
    }

    public Collection<Film> getFilmsList() {
        return filmStorage.getFilms().values();
    }

    public void addLikes(int filmId, int userId) {
        filmStorage.addLikes(filmId, userId);
    }

    public void deleteLikes(int filmId, int userId) {
        filmStorage.deleteLikes(filmId, userId);
    }

    public Collection<String> getPopularFilms(int count) {
        Comparator<Set<Integer>> compareSet = Comparator.comparingInt(Set::size);

        Map<Integer, Set<Integer>> collected = filmStorage.getPopularFilms(count).entrySet().stream().sorted(comparingByValue(compareSet))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (entry1, entry2) -> entry2, LinkedHashMap::new));

        HashMap<Integer, Film> films = (HashMap<Integer, Film>) filmStorage.getFilms();

        return collected.keySet().stream()
                .map(films::get)
                .map(Film::getName)
                .limit(count)
                .collect(Collectors.toList());
    }
}
