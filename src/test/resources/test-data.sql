insert into users (email, login, user_name, birthday)
    values ('email1@test.ru', 'login1', 'user1', '2000-10-01');
insert into users (email, login, user_name, birthday)
    values ('email2@test.ru', 'login2', 'user2', '2000-10-02');
insert into users (email, login, user_name, birthday)
    values ('email3@test.ru', 'login3', 'user3', '2000-10-03');
insert into FILMS (film_name, description, release_date, duration)
    VALUES ('film1', 'description1', '2000-10-01', 120);
insert into FILMS (film_name, description, release_date, duration)
    VALUES ('film2', 'description2', '2000-10-02', 100);
insert into FILMS (film_name, description, release_date, duration)
    VALUES ('film3', 'description3', '2000-10-03', 150);
insert into mpa_rating (mpa_id, mpa_rate)
    values((1), ('G'));
merge into mpa_rating (mpa_id, mpa_rate)
    values((2), ('PG'));
merge into mpa_rating (mpa_id, mpa_rate)
    values((3), ('PG-13'));
merge into mpa_rating (mpa_id, mpa_rate)
    values((4), ('R'));
merge into mpa_rating (mpa_id, mpa_rate)
    values((5), ('NC-17'));
merge into genre (genre_id, genre_name)
    values((1), ('Комедия'));
merge into genre (genre_id, genre_name)
    values((2),('Драма'));
merge into genre (genre_id, genre_name)
    values((3), ('Мультфильм'));
merge into genre (genre_id, genre_name)
    values((4), ('Триллер'));
merge into genre (genre_id, genre_name)
    values((5), ('Документальный'));
merge into genre (genre_id, genre_name)
    values((6), ('Боевик'));
