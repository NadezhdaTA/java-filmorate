package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.storage.mappers.GenreRowMapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
@Qualifier("FilmDbStorage")
@RequiredArgsConstructor
public class FilmDbStorage extends FilmRowMapper implements FilmStorage {
    private final JdbcTemplate jdbc;
    private final FilmRowMapper mapper = new FilmRowMapper();

    private void checkMpaRating(Film film) {
        int mpaId = jdbc.queryForObject("SELECT COUNT(mpa_rate) FROM mpa_rating WHERE mpa_id = ?",
                Integer.class, film.getMpaRating().getMpaId());
        if (mpaId == 0) {
            throw new NotFoundException("Неверный mpaId = " + film.getMpaRating().getMpaId());
        }
    }

    private void checkGenre(Film film) {
        for (Genre genre : film.getGenre()) {
            int mpaId = jdbc.queryForObject("SELECT COUNT(genre_name) FROM genre WHERE genre_id = ?",
                    Integer.class, genre.getGenreId());
            if (mpaId == 0) {
                throw new NotFoundException("Жанр с id = " + genre.getGenreId() + " не найден.");
            }
        }
    }

    private void getGenre(Film film) {
        Set<Genre> genres = new HashSet<>(jdbc.query("SELECT g.genre_id, g.genre_name " +
                        "FROM genre g JOIN genres gs on g.genre_id = gs.genre_id " +
                        "WHERE film_id = ?", new GenreRowMapper(), film.getId()));
        film.setGenre(genres);
    }

    private void setGenres(Film film) {
        List<Genre> genres = new ArrayList<>(film.getGenre());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        jdbc.batchUpdate("INSERT INTO genres (film_id, genre_id) VALUES (?, ?)", new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, film.getId());
                ps.setInt(2, genres.get(i).getGenreId());

            }

            @Override
                public int getBatchSize() {
                return genres.size();
            }
        });
        stopWatch.stop();
    }


    @Override
    public Film createFilm(Film film) {
        checkMpaRating(film);

        String sqlQuery = "INSERT INTO films (film_name, description, " +
                "release_date, duration, mpa_id) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"FILM_ID"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpaRating().getMpaId());
            return stmt;
            }, keyHolder);
        film.setId(keyHolder.getKey().intValue());

        checkGenre(film);
        setGenres(film);

        return film;
    }

    @Override
    public Film updateFilm(Film newFilm) {
        checkMpaRating(newFilm);

        jdbc.update("UPDATE films SET film_name = ?, description = ?, release_date = ?, " +
                        "duration = ?, mpa_id = ? WHERE film_id = ?",
                newFilm.getName(), newFilm.getDescription(), newFilm.getReleaseDate(),
                newFilm.getDuration(), newFilm.getMpaRating().getMpaId(), newFilm.getId());

        newFilm.getGenre().clear();
        if (newFilm.getGenre() != null) {
            checkGenre(newFilm);
            setGenres(newFilm);
        }
        return newFilm;
    }

    @Override
    public Collection<Film> getFilms() {
        Collection<Film> films = jdbc.query("SELECT * FROM films f, mpa_rating m " +
                "WHERE f.mpa_id = m.mpa_id ORDER BY film_id", mapper);

        for (Film film : films) {
            getGenre(film);
        }
        return films;
    }

    @Override
    public Optional<Film> getFilmById(int id) {
        Optional<Film> film = Optional.ofNullable(jdbc.queryForObject(
                "SELECT * FROM films f, mpa_rating m WHERE f.mpa_id = m.mpa_id AND film_id = ?",
                new FilmRowMapper(), id));

        if (film.get() != null) {
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
                "SELECT film_id FROM likes GROUP BY film_id ORDER BY COUNT(user_id) DESC LIMIT ?",
                Integer.class, count);

        Collection<Film> films = new ArrayList<>();
        if (!filmIds.isEmpty()) {
            for (Integer filmId : filmIds) {
                Film film = jdbc.queryForObject("SELECT * FROM films f, mpa_rating m " +
                                "WHERE f.mpa_id = m.mpa_id AND film_id = ?",
                        new FilmRowMapper(), filmId);
                getGenre(film);
                films.add(film);
            }
        }
        return films;
    }
}
