package ch.ge.apside.archi.cart.service.controller.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="${gateway.server.name}", path = "item")
public interface GatewayItemFeignClient {
    @RequestMapping("/list")
    String items();
}

//http://localhost:8090/item-service/cart/message
