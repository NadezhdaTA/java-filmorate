package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.db.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.db.UserDbStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmDbStorage.class, UserDbStorage.class})
public class FilmDbStorageTest extends TestData {
    private final FilmDbStorage filmStorage;
    private final UserDbStorage userStorage;

    static MpaRating getMpaRating1() {
        MpaRating mpaRating1 = new MpaRating();
        mpaRating1.setMpaId(1);
        mpaRating1.setMpaRate("G");
        return mpaRating1;
    }

    @Test
    public void createFilmTest() {
        Film film = getFilm3();
        film.setMpaRating(getMpaRating1());
        film = filmStorage.createFilm(film);

        Optional<Film> userOptional = filmStorage.getFilmById(film.getId());

        assertThat(userOptional)
                .isPresent()
                .get()
                .usingRecursiveComparison().ignoringFields("mpaRating", "id")
                .isEqualTo(getFilm3());
        assertThat(film.getMpaRating().getMpaId()).isEqualTo(getMpaRating1().getMpaId());
        assertNotNull(film.getId());
    }

    @Test
    public void getFilmByIdTest() {
        Film film = getFilm1();
        film.setMpaRating(getMpaRating1());
        filmStorage.createFilm(film);

        Optional<Film> userOptional = filmStorage.getFilmById(film.getId());

        assertNotNull(film.getId());
        assertThat(userOptional)
                .isPresent()
                .get()
                .usingRecursiveComparison().ignoringFields("mpaRating", "id")
                .isEqualTo(getFilm1());

    }

    @Test
    public void getFilmsTest() {
        Film film = getFilm1();
        film.setMpaRating(getMpaRating1());
        filmStorage.createFilm(film);

        Film film1 = getFilm2();
        film.setMpaRating(getMpaRating1());
        filmStorage.createFilm(film);

        Film film2 = getFilm3();
        film.setMpaRating(getMpaRating1());
        filmStorage.createFilm(film);

        Collection<Film> films = filmStorage.getFilms();
        assertThat(films).isNotNull();
        assertThat(films.size()).isEqualTo(3);
    }

    @Test
    public void getPopularFilmsTest() {

        Film film = getFilm1();
        film.setMpaRating(getMpaRating1());
        film = filmStorage.createFilm(film);

        Film film1 = getFilm2();
        film1.setMpaRating(getMpaRating1());
        film1 = filmStorage.createFilm(film1);

        User user = getUser1();
        user = userStorage.createUser(user);

        User user1 = getUser2();
        user1 = userStorage.createUser(user1);

        User user2 = getUser3();
        user2 = userStorage.createUser(user2);

        filmStorage.addLikes(film.getId(), user.getId());
        filmStorage.addLikes(film1.getId(), user1.getId());
        filmStorage.addLikes(film.getId(), user2.getId());

        ArrayList<Film> films = new ArrayList<>(filmStorage.getPopularFilms(10));
        assertThat(films).isNotNull();
        assertThat(films.size()).isEqualTo(2);

        ArrayList<Film> films1 = new ArrayList<>();
        films1.add(film);
        films1.add(film1);

        assertThat(films).isEqualTo(films1);

        assertThat(films.getFirst()).usingRecursiveComparison()
                .ignoringFields("id", "mpaRating")
                .isEqualTo(getFilm1());

        filmStorage.deleteLikes(film1.getId(), user1.getId());
        assertThat(filmStorage.getPopularFilms(10).size()).isEqualTo(1);
    }

}
