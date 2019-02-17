package ru.reeson2003.amuletproxy.handling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class SimpleProcessor implements RequestProcessor<RequestEntity<?>, byte[]> {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private UrlSwitch urlSwitch;

    @Override
    public Response<byte[]> process(Request<RequestEntity<?>> request) {
        log.debug("Got request");
        RequestEntity<?> requestEntity = request.getData();
        log.debug(requestEntity.toString());
        String oldUrl = requestEntity.getUrl().toString();
        String newUrl = urlSwitch.replaceHostInUrl(oldUrl);
        log.debug("Processing request to: {}", newUrl);
        byte[] response = restTemplate.getForObject(newUrl, byte[].class);
        return new Response<>(response);
    }

}
