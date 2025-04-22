package ru.yandex.practicum.filmorate.storage.mappers;

import org.springframework.jdbc.core.RowMapper;

import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MpaRowMapper implements RowMapper<MpaRating> {

    @Override
    public MpaRating mapRow(ResultSet rs, int rowNum) throws SQLException {
        final MpaRating mpa = new MpaRating();
        mpa.setMpaId(rs.getInt("mpa_id"));
        mpa.setMpaRate(rs.getString("mpa_rate"));
        return mpa;
    }
}
