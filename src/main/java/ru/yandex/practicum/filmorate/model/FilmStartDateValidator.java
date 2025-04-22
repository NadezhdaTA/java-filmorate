package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class FilmStartDateValidator implements ConstraintValidator<FilmStartDate, LocalDate> {
    LocalDate localDate = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value != null && value.isAfter(localDate);
    }

    @Override
    public void initialize(FilmStartDate constraintAnnotation) {
        LocalDate startDate = LocalDate.parse(constraintAnnotation.value());
    }
}
