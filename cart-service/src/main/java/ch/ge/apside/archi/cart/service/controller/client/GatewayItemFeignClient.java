package ch.ge.apside.archi.cart.service.controller.client;

import ch.ge.apside.archi.cart.service.model.Item;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name="${gateway.server.name}", path = "item")
public interface GatewayItemFeignClient {
    @RequestMapping("/list")
    List<Item> items();
}

//http://localhost:8090/item-service/cart/message
