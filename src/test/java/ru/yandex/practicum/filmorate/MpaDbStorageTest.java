package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.db.MpaDbStorage;

import java.util.ArrayList;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({MpaDbStorage.class})
public class MpaDbStorageTest extends TestData {
    private final MpaDbStorage storage;

    @Test
    public void getMpaRatingByIdTest() {
        Optional<MpaRating> result = storage.getMpaRatingById(1);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(getMpaRating1());
    }

    @Test
    public void getAllMpaRatingsTest() {
        ArrayList<MpaRating> result = new ArrayList<>(storage.getAllMpaRatings());

        assertThat(result.size()).isEqualTo(5);
    }

}
