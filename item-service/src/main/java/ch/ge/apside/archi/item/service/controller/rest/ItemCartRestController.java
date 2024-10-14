package ch.ge.apside.archi.item.service.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class ItemCartRestController {
    @GetMapping("/message")
    public String message(){
        return "ITEM CART MESSAGE";
    }

    @GetMapping("")
    public String empty(){
        return "ITEM CART EMPTY";
    }

    @GetMapping("/")
    public String slash(){
        return "ITEM CART SLASH";
    }

}
