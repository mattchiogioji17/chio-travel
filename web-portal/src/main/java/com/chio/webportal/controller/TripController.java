package com.chio.webportal.controller;

import com.chio.webportal.domain.User;
import com.chio.webportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.logging.Logger;

@RestController
@RequestMapping(value="/web-portal", produces = "application/json")
public class TripController {

    private Logger log = Logger.getLogger(TripController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/trips/{userId}")
    public String userFront(Principal principal, @PathVariable String userId) {
        User user = userService.findByUsername(principal.getName());
        log.info(">>>User Id: " + userId);
        String url = "http://trip-service/user/" + userId + "/trip/1";
        log.info(">>>URI: " + url);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        log.info(">>>Response Status: " + response.getStatusCodeValue());
        log.info(">>>Response Body: " + response.getBody());
        return response.getBody();
    }
}
