package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.db.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.db.UserDbStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmDbStorage.class, UserDbStorage.class})
public class FilmDbStorageTest extends TestData {
    private final FilmDbStorage filmStorage;



    static MpaRating getMpaRating1() {
        MpaRating mpaRating1 = new MpaRating();
        mpaRating1.setMpaId(1);
        mpaRating1.setMpaRate("G");
        return mpaRating1;
    }

    @Test
    public void testGetFilmById() {
        Optional<Film> userOptional = filmStorage.getFilmById(1);

        assertThat(userOptional)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(getFilm1());

    }

    @Test
    public void testGetFilms() {
        Collection<Film> films = filmStorage.getFilms();
        assertThat(films).isNotNull();
        assertThat(films.size()).isEqualTo(3);
    }

    @Test
    public void testGetPopularFilms() {
        filmStorage.addLikes(getFilm1().getId(), getUser1().getId());
        filmStorage.addLikes(getFilm1().getId(), getUser2().getId());
        filmStorage.addLikes(getFilm2().getId(), getUser3().getId());

        ArrayList<Film> films = new ArrayList<>(filmStorage.getPopularFilms(10));
        assertThat(films).isNotNull();
        assertThat(films.size()).isEqualTo(2);

        ArrayList<Film> films1 = new ArrayList<>();
        films1.add(getFilm1());
        films1.add(getFilm2());

        assertThat(films).isEqualTo(films1);
        assertThat(films.getFirst()).isEqualTo(getFilm1());

        filmStorage.deleteLikes(getFilm2().getId(), getUser3().getId());
        assertThat(filmStorage.getPopularFilms(10).size()).isEqualTo(1);
    }

}
