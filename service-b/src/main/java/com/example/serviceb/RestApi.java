package com.example.serviceb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RestApi {
    private static final Logger log = LoggerFactory.getLogger(RestApi.class);

    private RestTemplate restTemplate;

    public RestApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/info")
    public Map<String, Object> info()  {
        log.info("Calling `info` endpoint");

        var info = new HashMap<String, Object>();
        info.put("system_time", System.currentTimeMillis());
        info.put("service", "B");

        return info;
    }

    @GetMapping("/proxy/{host}/{port}/{path}")
    public Object proxy(@PathVariable String host, @PathVariable String port, @PathVariable String path)  {
        log.info("B Proxy to `{}:{}}/{}}`", host, port, path);


        String proxyUrl = "http://" + host + ":" + port + "/" + path;
        ResponseEntity<String> response = restTemplate.getForEntity(proxyUrl, String.class);
        log.info("Status code: {}", response.getStatusCode());

        return response.getBody();
    }

}
