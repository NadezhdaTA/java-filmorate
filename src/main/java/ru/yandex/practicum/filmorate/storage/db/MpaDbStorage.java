package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.interfaces.MpaStorage;
import ru.yandex.practicum.filmorate.storage.mappers.MpaRowMapper;

import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbc;
    private final MpaRowMapper mapper = new MpaRowMapper();

    @Override
    public Optional<MpaRating> getMpaRatingById(int id) {
        int mpaId = jdbc.queryForObject("SELECT COUNT(mpa_rate) FROM mpa_rating WHERE mpa_id = ?",
                Integer.class, id);

        if (mpaId != 0) {
        return Optional.ofNullable(jdbc.queryForObject("SELECT * FROM mpa_rating WHERE mpa_id = ?",
                new MpaRowMapper(), id));
        } else {
            throw new NotFoundException("Неверный mpaId = " + id);
        }
    }

    @Override
    public Collection<MpaRating> getAllMpaRatings() {
        return jdbc.query("SELECT * FROM mpa_rating", new MpaRowMapper());
    }
}
