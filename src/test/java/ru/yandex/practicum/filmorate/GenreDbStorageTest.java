package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.db.GenreDbStorage;

import java.util.ArrayList;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({GenreDbStorage.class})
public class GenreDbStorageTest extends TestData{
    private final GenreDbStorage storage;

    @Test
    public void testGetGenreById() {
        Optional<Genre> genre = storage.getGenreById(3);

        assertThat(genre).isPresent();
        assertThat(genre.get()).isEqualTo(getGenre1());
    }

    @Test
    public void testGetAllGenres() {
        ArrayList<Genre> genres = new ArrayList<>(storage.getAllGenres());

        assertThat(genres.size()).isEqualTo(6);
    }
}
