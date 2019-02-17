package ru.reeson2003.amuletproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.RequestEntity;
import org.springframework.web.socket.WebSocketSession;
import ru.reeson2003.amuletproxy.controller.ws.AmuletConnection;
import ru.reeson2003.amuletproxy.controller.ws.Connection;
import ru.reeson2003.amuletproxy.controller.ws.WebSocket;
import ru.reeson2003.amuletproxy.handling.RequestProcessor;
import ru.reeson2003.amuletproxy.handling.SimpleProcessor;

@SpringBootApplication
public class AmuletProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmuletProxyApplication.class, args);
    }

    @Bean
    public RequestProcessor<RequestEntity<?>, byte[]> requestProcessor() {
        return new SimpleProcessor();
    }

    @Bean
    public WebSocket webSocket() {
        return new WebSocket() {
            @Override
            protected String context() {
                return "ws";
            }
        };
    }

    @Bean
    @Lazy
    public Connection connection(WebSocketSession session) {
        return new AmuletConnection(session);
    }

}

