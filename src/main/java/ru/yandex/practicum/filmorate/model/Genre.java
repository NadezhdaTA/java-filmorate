package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "genreId")
public class Genre {
    @JsonProperty("id")
    private int genreId;
    @JsonProperty("name")
    private String genreName;
}
