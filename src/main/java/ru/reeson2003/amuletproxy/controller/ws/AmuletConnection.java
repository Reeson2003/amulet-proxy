package ru.reeson2003.amuletproxy.controller.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketSession;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import ru.reeson2003.amuletproxy.handling.UrlSwitch;

import java.time.Duration;

@Slf4j
public class AmuletConnection extends BaseConnection {

    @Value("${proxy.polling interval}")
    private long interval;
    @Autowired
    private UrlSwitch urlSwitch;
    private final RestTemplate restTemplate = new RestTemplate();
    private String pollingUrl = null;
    private Disposable subscription = () -> { /*no op*/ };

    public AmuletConnection(WebSocketSession session) {
        super(session);
    }

    @Override
    public void onMessage(String text) {
        log.info("Got message [{}]", text);
        pollingUrl = urlSwitch.replaceHostInUrl(text);
        subscription = Flux.interval(Duration.ofMillis(interval))
                .map(l -> restTemplate.getForObject(pollingUrl, String.class))
                .map(this::processResponse)
                .subscribe(this::send);
    }

    public String processResponse(String response) {
        return response;
    }

}
