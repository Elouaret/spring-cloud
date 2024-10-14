package ch.ge.apside.archi.item.service.controller.rest;

import ch.ge.apside.archi.item.service.controller.client.GatewayCartFeignClient;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/cartsFeign")
    public String cartsFeign(){
        return gatewayCartFeignClient.carts();
    }

    @GetMapping("/")
    public String index() {
        return String.format("Index from '%s'!", eurekaClient.getApplication(appName).getName());
    }

    // http://localhost:8090/item-service/item/list
    @GetMapping("/list")
    public String list() {
        String list = """
                {
                  id: 1,
                  name: Téléphone
                  price: 1000€
                },
                {
                  id: 2,
                  name: Ecran
                  price: 500€
                }
                """;
        return list;
    }

    @GetMapping("/name")
    public String name() {
        return String.format("Name from '%s'!", eurekaClient.getApplication(appName).getName());
    }
}
