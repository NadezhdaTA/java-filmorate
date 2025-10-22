package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@EqualsAndHashCode(of = {"id"})
public class Review {
    private Integer reviewId;
    private String content;
    private Boolean isPositive;
}
