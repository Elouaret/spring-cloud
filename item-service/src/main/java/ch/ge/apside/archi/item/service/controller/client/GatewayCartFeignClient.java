package ch.ge.apside.archi.item.service.controller.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="${gateway.server.name}", path = "cart")
public interface GatewayCartFeignClient {
    @RequestMapping("/list")
    String carts();
}

