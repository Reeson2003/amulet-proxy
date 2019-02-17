package ru.reeson2003.amuletproxy.handling;

import lombok.Data;

@Data
public class Response<T> {
    private final T data;
}
