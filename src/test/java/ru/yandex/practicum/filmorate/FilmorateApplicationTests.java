package ru.yandex.practicum.filmorate;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FilmorateApplicationTests {
	/*private final UserDbStorage userStorage;
	private final UserService userService = new UserService(userStorage);
	private final FilmService filmService = new FilmService();
	private final UserController userController = new UserController(userService);
	private final FilmController filmController = new FilmController(filmService);

	private User user = new User("user@test", "testLogin");
	private Film film = new Film("testFilm");

    FilmorateApplicationTests(UserDbStorage userStorage) {this.userStorage = userStorage;}

    @Test
	void getUsersListTest() {
		assertEquals(0, userController.getUsersList().size());

		userController.createUser(user);

		assertEquals(1, userController.getUsersList().size());

		User user1 = new User("user1@test", "test1Login");
		userController.createUser(user1);

		assertEquals(2, userController.getUsersList().size());
	}

	@Test
	void createUserTest() {
		assertEquals(0, userController.getUsersList().size());

		userController.createUser(user);

		assertEquals(1, userController.getUsersList().size());

		User user1 = new User("user1@test", "testLogin1");
		LocalDate date = LocalDate.of(1985, 3,12);
		user1.setBirthday(date);

		userController.createUser(user1);

	}

	@Test
	void userEmailTest() {
		assertEquals(0, userController.getUsersList().size());

		userController.createUser(user);

		assertEquals(1, userController.getUsersList().size());

		User user1 = new User("testMail", "testLogin1");

		assertEquals(1, userController.getUsersList().size());
	}

	@Test
	void userLoginTest() {
		assertEquals(0, userController.getUsersList().size());

		userController.createUser(user);

		assertEquals(1, userController.getUsersList().size());

		User user1 = new User("test@mail", "test Login1");

		assertEquals(1, userController.getUsersList().size());
	}

	@Test
	void userBirthdayTest() {
		assertEquals(0, userController.getUsersList().size());

		LocalDate date = LocalDate.of(1985,3, 12);
		user.setBirthday(date);
		userController.createUser(user);

		assertEquals(1, userController.getUsersList().size());

		User user1 = new User("test@mail", "testLogin1");
		LocalDate date1 = LocalDate.of(2026,3, 12);
		user1.setBirthday(date1);

		assertEquals(1, userController.getUsersList().size());
	}

	@Test
	void updateUserTest() throws NotFoundException, DuplicatedDataException {
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
	void getUserById() throws NotFoundException {
		userController.createUser(user);
		User user1 = new User("user@test1", "testLogin1");
		userController.createUser(user1);

		assertEquals(2, userController.getUsersList().size());

		assertEquals(user1, userController.getUserById(2));
	}

	@Test
	void getFilmsListTest() {
		assertEquals(0, filmController.getFilmsList().size());

		filmController.createFilm(film);

		assertEquals(1, filmController.getFilmsList().size());

		Film film1 = new Film("filmTest1");
		filmController.createFilm(film1);

		assertEquals(2, filmController.getFilmsList().size());
	}

	@Test
	void createFilmTest() {
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
	void filmDescriptionTest() {
		filmController.createFilm(film);

		assertEquals(1, filmController.getFilmsList().size());

		Film film1 = new Film("filmTest1");
		film1.setDescription("DescriptionDescriptionDescriptionDescriptionDescriptionDescription" +
				"DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription" +
				"DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription");

		assertEquals(1, filmController.getFilmsList().size());
	}

	@Test
	void filmReleaseDateTest() {
		filmController.createFilm(film);

		assertEquals(1, filmController.getFilmsList().size());

		Film film1 = new Film("filmTest1");
		LocalDate date = LocalDate.of(1895, 12, 25);
		film1.setReleaseDate(date);

		assertEquals(1, filmController.getFilmsList().size());
	}

	@Test
	void filmDurationTest() {
		filmController.createFilm(film);

		assertEquals(1, filmController.getFilmsList().size());

		Film film1 = new Film("filmTest1");
		film1.setDuration(-15);

		assertEquals(1, filmController.getFilmsList().size());
	}

	@Test
	void updateFilmTest() throws NotFoundException, DuplicatedDataException {
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

	/*@Test
	void getFriendsListTest() throws NotFoundException {
		userController.createUser(user);
		User user1 = userController.createUser(new User("name@l", "login"));
		User user2 = userController.createUser(new User("name@lm", "logins"));
		Friends friends1 = new Friends(user.getId(), user1.getId(), FriendsStatus.UNCONFIRMED);
		Friends friends2 = new Friends(user.getId(), user2.getId(), FriendsStatus.CONFIRMED);

		userController.addFriend(user.getId(), user1.getId(), friends1.getFriendship());
		userController.addFriend(user.getId(), user2.getId(), friends2.getFriendship());
		assertEquals(2, userController.getUsersFriends(user.getId()).size());

		userController.deleteFriend(user.getId(), 2);

		assertEquals(2, userController.getUsersFriends(user.getId()).size());

	}

	@Test
	void addLikesTest() {
		filmController.createFilm(film);
		userController.createUser(user);

		filmController.addLikes(film.getId(), user.getId());

		Collection<String> filmNames = filmController.getPopularFilmList(film.getId());

		assertEquals(1, filmController.getPopularFilmList(film.getId()).size());

		filmController.deleteFilmLikes(film.getId(), user.getId());

		assertEquals(0, filmController.getPopularFilmList(film.getId()).size());
	}*/

}
