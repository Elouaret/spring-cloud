package ch.ge.apside.archi.gateway.controller;

//import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
//@RequestMapping("/circuitbreakerfallback")
public class RouteController {

    private static final String SERVICE_NAME = "cart-service";
    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    private EurekaClient eurekaClient;

    @RequestMapping("/get-greeting-no-feign")
    public String greeting(Model model) {

        InstanceInfo service = eurekaClient
                .getApplication(SERVICE_NAME)
                .getInstances()
                .get(0);

        String hostName = service.getHostName();
        int port = service.getPort();

        URI url = URI.create("http://" + hostName + ":" + port + "/greeting");

        ResponseEntity<String> response = new RestTemplate().getForEntity(url, String.class);

        model.addAttribute("greeting", response.getBody());

        return "page-view";
    }

    @GetMapping("/")
    public String index() {
        return String.format("Index from '%s'!", eurekaClient.getApplication(appName).getName());
    }


    @RequestMapping("/circuitbreakerfallback")
    public String circuitbreakerfallback() {
        return "This is a fallback";
    }
}
