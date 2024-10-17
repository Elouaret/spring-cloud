package ch.ge.apside.archi.item.service.controller.rest;

import ch.ge.apside.archi.item.service.controller.client.GatewayCartFeignClient;
import ch.ge.apside.archi.item.service.controller.model.Item;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemRestController {

    @Autowired
    private GatewayCartFeignClient gatewayCartFeignClient;

    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/")
    public String index() {
        return String.format("Index from '%s'!", eurekaClient.getApplication(appName).getName());
    }

    @GetMapping(value = "/list", produces = { "application/json" })
    ResponseEntity<List<Item>> list() {
        List<Item> items = List.of(new Item(1L, "Téléphone", "1000€"), new Item(2L, "Ecran", "500€"));
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping(value = "/cartsFeign", produces = { "application/json" })
    public String cartsFeign(){
        return gatewayCartFeignClient.carts();
    }

}
