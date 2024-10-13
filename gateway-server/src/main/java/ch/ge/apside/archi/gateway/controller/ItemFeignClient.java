package ch.ge.apside.archi.gateway.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

//@FeignClient("item-service")
public interface ItemFeignClient {
    @RequestMapping("/list")
    String items();
}
