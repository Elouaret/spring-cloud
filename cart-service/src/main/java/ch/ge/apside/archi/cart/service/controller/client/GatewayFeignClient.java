package ch.ge.apside.archi.cart.service.controller.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

//@FeignClient(name = "gateway", url = "${spring.cloud.discovery.client.simple.instances.${gateway.server.name}.uri}")
//@FeignClient(name = "gateway", url = "${spring.cloud.gateway.routes[0].uri}")
//@FeignClient(name = "${gateway.server.name}")
public interface GatewayFeignClient {
    @RequestMapping("/${item.service.name}/list")
    String items();
}

//@Value("${gateway.service.name}")
//private String gatewayServiceName;

//http://localhost:8090/item-service/cart/message
