package ch.ge.apside.archi.cart.service.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("item-service")
public interface ItemClient {
    @RequestMapping("/name")
    String name();
}
