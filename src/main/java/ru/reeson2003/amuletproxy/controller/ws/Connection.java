package ru.reeson2003.amuletproxy.controller.ws;

import org.springframework.web.socket.CloseStatus;

public interface Connection {

    void onOpen();

    void onMessage(String text);

    void onError(Throwable exception);

    void onClose(CloseStatus status);

    void send(String message);

}