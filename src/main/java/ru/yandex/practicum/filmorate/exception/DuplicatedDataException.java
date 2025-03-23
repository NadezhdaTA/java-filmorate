package ru.yandex.practicum.filmorate.exception;

public class DuplicatedDataException extends RuntimeException {

    public DuplicatedDataException() {
        super();
    }

    public DuplicatedDataException(String message) {
        super(message);
    }
}
