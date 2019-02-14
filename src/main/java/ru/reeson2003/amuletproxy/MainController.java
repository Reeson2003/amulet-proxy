package ru.reeson2003.amuletproxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;

@Slf4j
@RestController
public class MainController {

    @Value("${proxy.domain}")
    private String domain;

    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(method = RequestMethod.GET)
    public byte[] proxyGet(RequestEntity<?> requestEntity) throws URISyntaxException {
        log.debug("Got request");
        log.debug(requestEntity.toString());
        String oldUrl = requestEntity.getUrl().toString();
        String newUrl = replaceHostInUrl(oldUrl, domain);
        log.debug("Processing request to: {}", newUrl);
        return restTemplate.getForObject(newUrl, byte[].class);
    }

    @RequestMapping(method = RequestMethod.POST)
    public byte[] proxyPost(HttpServletRequest request) throws URISyntaxException {
        log.debug("Got request");
        log.debug(request.toString());
        String oldUrl = request.getRequestURI();
        String newUrl = replaceHostInUrl(oldUrl, domain);
        Enumeration<String> headerNames = request.getHeaderNames();
        return "no op".getBytes();
    }

    public static String replaceHostInUrl(String originalURL, String newAuthority) throws URISyntaxException {
        URI uri = new URI(originalURL);
        URI newUri = new URI(newAuthority);
        uri = new URI(newUri.getScheme(), newUri.getAuthority(), uri.getPath(), uri.getQuery(), uri.getFragment());
        return uri.toString();
    }
}
