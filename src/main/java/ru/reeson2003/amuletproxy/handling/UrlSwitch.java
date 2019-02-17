package ru.reeson2003.amuletproxy.handling;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class UrlSwitch {

    @Value("${proxy.domain}")
    private String domain;

    public String replaceHostInUrl(String originalURL) {
        try {
            URI uri = new URI(originalURL);
            URI newUri = new URI(domain);
            uri = new URI(newUri.getScheme(), newUri.getAuthority(), uri.getPath(), uri.getQuery(), uri.getFragment());
            return uri.toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Can't process url " + originalURL, e);
        }
    }
}
