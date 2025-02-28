package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.Duration;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Film {

    @NonNull
    private String name;

    private int id;
    private String description;
    private LocalDate releaseDate;
    private Duration duration;

}
