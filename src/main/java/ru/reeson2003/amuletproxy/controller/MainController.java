package ru.reeson2003.amuletproxy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.reeson2003.amuletproxy.handling.Request;
import ru.reeson2003.amuletproxy.handling.RequestProcessor;

@Slf4j
@RestController
public class MainController {

    @Autowired
    private RequestProcessor<RequestEntity<?>, byte[]> processor;

    @RequestMapping(method = RequestMethod.GET)
    public byte[] proxyGet(RequestEntity<?> requestEntity) {
        return processor.process(new Request<>(requestEntity)).getData();
    }

}
