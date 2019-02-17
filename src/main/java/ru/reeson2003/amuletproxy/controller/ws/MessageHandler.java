package ru.reeson2003.amuletproxy.controller.ws;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Slf4j
@Component
public class MessageHandler
        extends TextWebSocketHandler {

    private final Map<WebSocketSession, Connection> connections = new ConcurrentHashMap<>();

    @Autowired
    private ApplicationContext context;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Connection connection = context.getBean(Connection.class, session);
        connections.put(session, connection);
        connection.onOpen();
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        Optional.ofNullable(connections.get(session))
                .orElseThrow(IllegalArgumentException::new)
                .onMessage(message.getPayload());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        Optional.ofNullable(connections.get(session))
                .orElseThrow(IllegalArgumentException::new)
                .onError(exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Optional.ofNullable(connections.remove(session))
                .orElseThrow(IllegalArgumentException::new)
                .onClose(status);
    }

}