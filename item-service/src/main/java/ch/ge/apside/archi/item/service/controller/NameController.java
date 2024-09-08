package ch.ge.apside.archi.item.service.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public interface NameController {
    @RequestMapping("/name")
    String name();
}