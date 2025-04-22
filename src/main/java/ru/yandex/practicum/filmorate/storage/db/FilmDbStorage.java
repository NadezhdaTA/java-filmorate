package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.storage.mappers.GenreRowMapper;


import java.util.*;
import java.util.stream.Collectors;


@Repository
@Qualifier
@RequiredArgsConstructor
public class FilmDbStorage extends FilmRowMapper implements FilmStorage {
    private final JdbcTemplate jdbc;
    @Qualifier("filmRowMapper")
    private final FilmRowMapper mapper;

    private void getMpaRating(Film film) {
        int mpaId = jdbc.queryForObject("SELECT IFNULL(mpa_id, 0) FROM films WHERE film_id = ?",
                Integer.class, film.getId());
        if (mpaId > 0) {
            String mpaRate = jdbc.queryForObject("SELECT mpa_rate FROM mpa_rating WHERE mpa_id = ?",
                    String.class, mpaId);
            film.setMpaRating(new MpaRating(mpaId, mpaRate));
        }
    }

    private void getGenre(Film film) {
            List<Integer> genresIds = jdbc.queryForList("SELECT genre_id FROM genres WHERE film_id = ?",
                    Integer.class, film.getId());

            Set<Genre> genres = genresIds.stream()
                    .map(integer -> jdbc.queryForObject("SELECT genre_id, genre_name FROM genre WHERE genre_id = ?",
                                    new GenreRowMapper(), integer))
                    .collect(Collectors.toSet());
            film.setGenre(genres);
        }


    @Override
    public Film createFilm(Film film) {
        if (film.getMpaRating() != null) {
            int mpaId = jdbc.queryForObject("SELECT COUNT(mpa_rate) FROM mpa_rating WHERE mpa_id = ?",
                    Integer.class, film.getMpaRating().getMpaId());

            if (mpaId != 0) {

                jdbc.update("INSERT INTO films (film_name, description, release_date, duration, mpa_id) " +
                                "VALUES (?, ?, ?, ?, ?)", film.getName(), film.getDescription(), film.getReleaseDate(),
                        film.getDuration(), film.getMpaRating().getMpaId());

                film.setId(jdbc.queryForObject("SELECT MAX(film_id) FROM films", Integer.class));
            } else {
                throw new NotFoundException("Неверный mpaId = " + film.getMpaRating().getMpaId());
            }
        }

        if (film.getGenre() != null && !film.getGenre().isEmpty()) {
            for (Genre genre : film.getGenre()) {
                if (genre.getGenreId() < 7) {
                    jdbc.update("INSERT INTO genres VALUES (?, ?)", film.getId(), genre.getGenreId());

                } else {
                    throw new NotFoundException("Жанр с id = " + genre.getGenreId() + " не найден.");
                }
            }
        }

        return film;
    }

    @Override
    public Film updateFilm(Film newFilm) {
        if (newFilm.getMpaRating().getMpaId() > 5) {
            throw new NotFoundException("MPA с id = " + newFilm.getMpaRating().getMpaId() + " не найден.");
        }

        jdbc.update("UPDATE films SET film_name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? WHERE film_id = ?",
                newFilm.getName(), newFilm.getDescription(), newFilm.getReleaseDate(),
                newFilm.getDuration(), newFilm.getMpaRating().getMpaId(), newFilm.getId());

        if (newFilm.getMpaRating() != null) {
            getMpaRating(newFilm);
        }

        if (newFilm.getGenre() != null && !newFilm.getGenre().isEmpty()) {
            newFilm.getGenre().clear();
        }

        getGenre(newFilm);
        return newFilm;
    }

    @Override
    public Collection<Film> getFilms() {
        Collection<Film> films = jdbc.query("SELECT * FROM films", mapper);
        for (Film film : films) {
            getMpaRating(film);
            getGenre(film);
        }
        return films;
    }

    @Override
    public Optional<Film> getFilmById(int id) {
        Optional<Film> film = Optional.ofNullable(jdbc.queryForObject(
                "SELECT * FROM films WHERE film_id = ?", new FilmRowMapper(), id));

        if (film.get() != null) {
            getMpaRating(film.get());
            getGenre(film.get());
        }
        return film;
    }

    @Override
    public void addLikes(int filmId, int userId) {
        jdbc.update("INSERT INTO likes (film_id, user_id) VALUES (?, ?)", filmId, userId);
    }

    @Override
    public void deleteLikes(int filmId, int userId) {
        jdbc.update("DELETE FROM likes WHERE film_id = ? AND user_id = ?", filmId, userId);
    }

    @Override
    public Collection<Film> getPopularFilms(int count) {
        List<Integer> filmIds = jdbc.queryForList(
                "SELECT film_id FROM likes GROUP BY film_id ORDER BY COUNT(user_id) DESC LIMIT ?", Integer.class, count);

        Collection<Film> films = new ArrayList<>();
        if (!filmIds.isEmpty()) {
            for (Integer filmId : filmIds) {
                Film film = jdbc.queryForObject("SELECT * FROM films WHERE film_id = ?", new FilmRowMapper(), filmId);
                getMpaRating(film);
                getGenre(film);
                films.add(film);
            }
        }
        return films;
    }

}
