package ch.ge.apside.archi.cart.service.controller;

import ch.ge.apside.archi.cart.service.controller.client.GatewayFeignClient;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class ConsumerController {

//    @Autowired
//    private GatewayFeignClient gatewayFeignClient;

    @GetMapping("/message")
    public String message(){
        return "CART MESSAGE";
    }

    @GetMapping("")
    public String empty(){
        return "CART EMPTY";
    }

    @GetMapping("/")
    public String slash(){
        return "CART SLASH";
    }

    // http://localhost:8090/cart-service/cart/items
    @GetMapping("/items")
    public String item(){
//        return gatewayFeignClient.items();
        String itemsEndPoint = getItemUrl() + "/item/list";
//        WebClient webClient = WebClient.create(itemsEndPoint);
        WebClient webClient = WebClient.builder()
                .baseUrl(itemsEndPoint)
//                .defaultCookie("cookieKey", "cookieValue")
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", itemsEndPoint))
                .build();
        WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec = webClient.get();
        WebClient.ResponseSpec retrieve = requestHeadersUriSpec.retrieve();
        Mono<String> stringMono = retrieve.bodyToMono(String.class);
        String items = stringMono.block();
        return items;
    }

    @Value("${gateway.server.name}")
    private String gatewayServerName;

    @Value("${item.service.name}")
    private String itemServiceName;


    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    private URI getServiceUri(String serviceName){
        Application application = eurekaClient.getApplication(serviceName);
        URI url = null;//URI.create("http://localhost:8080");
        if(application != null){
            List<InstanceInfo> instances = application.getInstances();
            if(!CollectionUtils.isEmpty(instances)){
                InstanceInfo service = instances.get(0);
                if(service != null){
                    String homePageUrl = service.getHomePageUrl();
                    if(StringUtils.isNotBlank(homePageUrl)){
                        if(homePageUrl.endsWith("/")){
                            homePageUrl = homePageUrl.substring(0, homePageUrl.length()-1);
                        }
                        url = URI.create(homePageUrl);
                    }
                }
            }
        }
        return url;
    }

    public URI getGatewayUrl(){
        return getServiceUri(gatewayServerName);
    }

    public String getItemUrl(){
        return getGatewayUrl().toString() + "/" + itemServiceName;
    }

}
