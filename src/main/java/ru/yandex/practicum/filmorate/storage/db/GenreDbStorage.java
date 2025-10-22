package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mappers.GenreRowMapper;

import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbc;

    @Override
    public Optional<Genre> getGenreById(int id) {
        int genre = jdbc.queryForObject("SELECT COUNT(genre_name) FROM genre WHERE genre_id = ?", Integer.class, id);
        if (genre != 0) {
            return Optional.ofNullable(jdbc.queryForObject("SELECT * FROM genre WHERE genre_id = ?",
                    new GenreRowMapper(), id));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Collection<Genre> getAllGenres() {
        return jdbc.query("SELECT * FROM genre", new GenreRowMapper());
    }

}
