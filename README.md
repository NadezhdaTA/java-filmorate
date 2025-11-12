# java-filmorate

## BD-structure
![](/DB-Diagram.png)

Получение списка фильмов по рейтингу:
```
SELECT f.film_id,
       COUNT(l.user_id)
FROM film AS f
INNER JOIN likes AS l ON f.film_id = l.film_id
GROUP BY f.film_id
ORDER BY COUNT(l.user_id) DESC;
```

Получение списка друзей пользователя:
```
SELECT u.user_id,
       fr.friend_id
FROM user AS u
INNER JOIN friends AS fr ON u.user_id = fr.user_id
GROUP BY u.user_id;
```

Получение списка названий фильмов в жанре COMEDY по продолжительности:
```
SELECT f.name,
       f.duration
FROM film AS f
JOIN film_genre AS fg ON f.film_id = fg.film_id
JION genre AS g ON fg.genre_id = g.genre_id
WHERE g.name = 'COMEDY'
GROUP BY g.name
ORDER BY f.duration;
```



