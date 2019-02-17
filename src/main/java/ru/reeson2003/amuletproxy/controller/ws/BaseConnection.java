package ru.reeson2003.amuletproxy.controller.ws;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public abstract class BaseConnection implements Connection {

    private final WebSocketSession session;

    @Override
    public void send(String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            throw new RuntimeException("Can't send message into socket", e);
        }
    }


    @Override
    public void onOpen() {
        log.info("Opened connection.");
    }

    @Override
    public void onError(Throwable exception) {
        log.error("Error occurred [{}]", exception);
    }

    @Override
    public void onClose(CloseStatus status) {
        log.info("Connection closed. [{}]", status);
    }
}
