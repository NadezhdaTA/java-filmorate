package ru.yandex.practicum.filmorate;


import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class TestData {

    static Film getFilm1() {
        Film film1 = new Film();
        film1.setId(1);
        film1.setName("film1");
        film1.setDescription("description1");
        film1.setDuration(120);
        film1.setReleaseDate(LocalDate.of(2000, 10, 1));
        return film1;
    }

    static Film getFilm2() {
        Film film2 = new Film();
        film2.setId(2);
        film2.setName("film2");
        film2.setDescription("description2");
        film2.setDuration(100);
        film2.setReleaseDate(LocalDate.of(2000, 10, 2));
        return film2;
    }

    static Film getFilm3() {
        Film film3 = new Film();
        film3.setId(3);
        film3.setName("film3");
        film3.setDescription("description3");
        film3.setDuration(150);
        film3.setReleaseDate(LocalDate.of(2000, 10, 1));
        return film3;
    }

    static User getUser1() {
        User user1 = new User();
        user1.setId(1);
        user1.setEmail("email1@test.ru");
        user1.setName("user1");
        user1.setLogin("login1");
        user1.setBirthday(LocalDate.of(2000, 10, 1));
        return user1;
    }

    static User getUser2() {
        User user2 = new User();
        user2.setId(2);
        user2.setEmail("email2@test.ru");
        user2.setName("user2");
        user2.setLogin("login2");
        user2.setBirthday(LocalDate.of(2000, 10, 2));
        return user2;
    }

    static User getUser3() {
        User user3 = new User();
        user3.setId(3);
        user3.setEmail("email3@test.ru");
        user3.setName("user3");
        user3.setLogin("login3");
        user3.setBirthday(LocalDate.of(2000, 10, 3));
        return user3;
    }

    static MpaRating getMpaRating1() {
        MpaRating mpaRating1 = new MpaRating();
        mpaRating1.setMpaId(1);
        mpaRating1.setMpaRate("G");
        return mpaRating1;
    }

    static Genre getGenre1() {
        Genre genre1 = new Genre();
        genre1.setGenreId(3);
        genre1.setGenreName("Мультфильм");
        return genre1;
    }
}
