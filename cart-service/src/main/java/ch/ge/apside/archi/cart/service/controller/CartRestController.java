package ch.ge.apside.archi.cart.service.controller;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cart")
@RefreshScope
@ConfigurationProperties
public class CartRestController {

    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${user.role}")
    private String role;

    @GetMapping("/")
    public String index() {
        return String.format("Index from '%s'!", eurekaClient.getApplication(appName).getName());
    }

    @GetMapping("/name")
    public String hello() {
        return String.format("Name from '%s'!", eurekaClient.getApplication(appName).getName());
    }

    @GetMapping("/role")
    public String role() {
        return String.format("Role : '%s'", role);
    }

    @Autowired
    private ItemClient itemClient;

    @GetMapping("/item")
    public String item() {
        return String.format("Item name : '%s'", itemClient.name());
    }

}
