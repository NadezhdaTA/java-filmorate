package ru.yandex.practicum.filmorate.exception;

public class DuplicatedDataException extends Exception{

    public DuplicatedDataException() { super(); }

    public DuplicatedDataException(String message) { super(message); }
}
