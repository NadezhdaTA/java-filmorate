package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmorateApplicationTests {
	private final UserController userController = new UserController();
	private final FilmController filmController = new FilmController();

	private User user = new User("user@test", "testLogin");
	private Film film = new Film("testFilm");

	@Test
	void contextLoads() {
	}

	@Test
	void getUsersListTest() throws ValidationException {
		assertEquals(0, userController.getUsersList().size());

		userController.createUser(user);

		assertEquals(1, userController.getUsersList().size());

		User user1 = new User("user1@test", "test1Login");
		userController.createUser(user1);

		assertEquals(2, userController.getUsersList().size());
	}

	@Test
	void createUserTest() throws ValidationException {
		assertEquals(0, userController.getUsersList().size());

		userController.createUser(user);

		assertEquals(1, userController.getUsersList().size());

		User user1 = new User("user1@test", "testLogin1");
		LocalDate date = LocalDate.of(1985, 3,12);
		user1.setBirthday(date);

		userController.createUser(user1);

	}

	@Test
	void userEmailTest() throws ValidationException {
		assertEquals(0, userController.getUsersList().size());

		userController.createUser(user);

		assertEquals(1, userController.getUsersList().size());

		User user1 = new User("testMail", "testLogin1");
		assertThrows(ValidationException.class, () -> userController.createUser(user1));

		assertEquals(1, userController.getUsersList().size());
	}

	@Test
	void userLoginTest() throws ValidationException {
		assertEquals(0, userController.getUsersList().size());

		userController.createUser(user);

		assertEquals(1, userController.getUsersList().size());

		User user1 = new User("test@mail", "test Login1");
		assertThrows(ValidationException.class, () -> userController.createUser(user1));

		assertEquals(1, userController.getUsersList().size());
	}

	@Test
	void userBirthdayTest() throws ValidationException {
		assertEquals(0, userController.getUsersList().size());

		LocalDate date = LocalDate.of(1985,3, 12);
		user.setBirthday(date);
		userController.createUser(user);

		assertEquals(1, userController.getUsersList().size());

		User user1 = new User("test@mail", "testLogin1");
		LocalDate date1 = LocalDate.of(2026,3, 12);
		user1.setBirthday(date1);
		assertThrows(ValidationException.class, () -> userController.createUser(user1));

		assertEquals(1, userController.getUsersList().size());
	}

	@Test
	void updateUserTest() throws ValidationException, NotFoundException, DuplicatedDataException {
		userController.createUser(user);

		assertEquals(1, userController.getUsersList().size());

		User user1 = new User("user1@test", "test1Login");
		user1.setId(user.getId());
		LocalDate date = LocalDate.of(1985,3, 12);
		user1.setBirthday(date);
		userController.updateUser(user1);

		assertEquals(user.getEmail(), user1.getEmail());
		assertEquals(user.getLogin(), user1.getLogin());
		assertEquals(user.getBirthday(), user1.getBirthday());

		assertEquals(1, userController.getUsersList().size());
	}

	@Test
	void getFilmsListTest() throws ValidationException {
		assertEquals(0, filmController.getFilmsList().size());

		filmController.createFilm(film);

		assertEquals(1, filmController.getFilmsList().size());

		Film film1 = new Film("filmTest1");
		filmController.createFilm(film1);

		assertEquals(2, filmController.getFilmsList().size());
	}

	@Test
	void createFilmTest() throws ValidationException {
		assertEquals(0, filmController.getFilmsList().size());

		filmController.createFilm(film);

		assertEquals(1, filmController.getFilmsList().size());

		Film film1 = new Film("Film1Test");
		film1.setDuration(90);
		LocalDate date = LocalDate.of(1997, 2,12);
		film1.setReleaseDate(date);
		film1.setDescription("Description1");
		filmController.createFilm(film1);

		assertEquals(2, filmController.getFilmsList().size());

	}

	@Test
	void filmDescriptionTest() throws ValidationException {
		filmController.createFilm(film);

		assertEquals(1, filmController.getFilmsList().size());

		Film film1 = new Film("filmTest1");
		film1.setDescription("DescriptionDescriptionDescriptionDescriptionDescriptionDescription" +
				"DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription" +
				"DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription");
		assertThrows(ValidationException.class, () -> filmController.createFilm(film1));

		assertEquals(1, filmController.getFilmsList().size());
	}

	@Test
	void filmReleaseDateTest() throws ValidationException {
		filmController.createFilm(film);

		assertEquals(1, filmController.getFilmsList().size());

		Film film1 = new Film("filmTest1");
		LocalDate date = LocalDate.of(1895, 12, 25);
		film1.setReleaseDate(date);
		assertThrows(ValidationException.class, () -> filmController.createFilm(film1));

		assertEquals(1, filmController.getFilmsList().size());
	}

	@Test
	void filmDurationTst() throws ValidationException {
		filmController.createFilm(film);

		assertEquals(1, filmController.getFilmsList().size());

		Film film1 = new Film("filmTest1");
		film1.setDuration(-15);
		assertThrows(ValidationException.class, () -> filmController.createFilm(film1));

		assertEquals(1, filmController.getFilmsList().size());
	}

	@Test
	void updateFilmTest() throws ValidationException, NotFoundException, DuplicatedDataException {
		filmController.createFilm(film);

		assertEquals(1, filmController.getFilmsList().size());

		Film film1 = new Film("filmTest1");
		film1.setDuration(120);
		film1.setDescription("TestDescription");
		film1.setId(film.getId());

		filmController.updateFilm(film1);

		assertEquals(film.getDescription(), film1.getDescription());
		assertEquals(film.getDuration(), film1.getDuration());
		assertEquals(film.getName(), film1.getName());

		assertEquals(1, filmController.getFilmsList().size());
	}

}
