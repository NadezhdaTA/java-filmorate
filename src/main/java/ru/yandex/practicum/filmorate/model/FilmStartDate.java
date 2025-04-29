package ru.yandex.practicum.filmorate.model;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(value = FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = FilmStartDateValidator.class)
@Documented
public @interface FilmStartDate {
    String message() default "Дата выхода фильма не может быть раньше {value}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default {};

    String value() default "1895-12-28";
}

