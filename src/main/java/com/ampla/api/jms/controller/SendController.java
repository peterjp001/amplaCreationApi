package com.ampla.api.jms.controller;


import com.ampla.api.jms.service.SendService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendController {

    private SendService service;

    public SendController(SendService service) {
        this.service = service;
    }

    @PostMapping(path="/send/{name}")
    @PostAuthorize("hasAnyAuthority('USER')")
    public String SendName(@PathVariable  String name){
        // DEFINE SERVICE CLASS
        service.sendNane(name);
        return name;
    }
}
