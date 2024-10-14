package ch.ge.apside.archi.cart.service.controller;

import ch.ge.apside.archi.cart.service.controller.client.GatewayItemFeignClient;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Controller
public class CartPageController {

    @Autowired
    private GatewayItemFeignClient gatewayItemFeignClient;

    private static final String SERVICE_NAME = "client-service";

    @Autowired
    private EurekaClient eurekaClient;

    @RequestMapping("/get-page")
    public String page(Model model) {
        model.addAttribute("items", gatewayItemFeignClient.items());
        return "page-view";
    }

    @RequestMapping("/get-greeting-no-feign")
    public String withEurekaClientInfos(Model model) {

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
}
