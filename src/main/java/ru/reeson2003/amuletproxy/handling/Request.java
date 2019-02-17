package ru.reeson2003.amuletproxy.handling;

import lombok.Data;

@Data
public class Request<T> {
    private final T data;
}
