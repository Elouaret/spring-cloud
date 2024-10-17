package ch.ge.apside.archi.cart.service.controller.rest;

import ch.ge.apside.archi.cart.service.configuration.ApplicationPropertiesConfiguration;
import ch.ge.apside.archi.cart.service.controller.client.GatewayItemFeignClient;
import ch.ge.apside.archi.cart.service.model.Cart;
import ch.ge.apside.archi.cart.service.model.Item;
import ch.ge.apside.archi.cart.service.model.Property;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/cart")
@RefreshScope
@ConfigurationProperties
public class CartRestController {

    @Autowired
    private ApplicationPropertiesConfiguration applicationPropertiesConfiguration;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${editique.url}")
    private String editiqueUrl;

    /********************* FEIGN CLIENT *********************/

    // http://localhost:8090/cart-service/cart/itemsFeign

    @Autowired
    private GatewayItemFeignClient gatewayItemFeignClient;

//    @GetMapping("/itemsFeign")
//    public String itemsFeign(){
//        return gatewayItemFeignClient.items();
//    }

    /********************* FEIGN CLIENT *********************/

    /********************* WEB CLIENT *********************/

    // http://localhost:8090/cart-service/cart/itemsWebClient

    @Value("${gateway.server.name}")
    private String gatewayServerName;

    @Value("${item.service.name}")
    private String itemServiceName;

    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    @GetMapping("/itemsWebClient")
    public String itemsWebClient(){
        String itemsEndPoint = getItemUrl() + "/item/list";
        WebClient webClient = WebClient.builder()
                .baseUrl(itemsEndPoint)
                .defaultUriVariables(Collections.singletonMap("url", itemsEndPoint))
                .build();
        WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec = webClient.get();
        WebClient.ResponseSpec retrieve = requestHeadersUriSpec.retrieve();
        Mono<String> stringMono = retrieve.bodyToMono(String.class);
        String items = stringMono.block();
        return items;
    }

    private URI getServiceUri(String serviceName){
        Application application = eurekaClient.getApplication(serviceName);
        URI url = null;
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

    /********************* WEB CLIENT *********************/

    @GetMapping("/")
    public String index(){
        return String.format("Index from '%s'!", eurekaClient.getApplication(appName).getName());
    }

    @GetMapping(value = "/{id}", produces = { "application/json" })
    public ResponseEntity<Cart> getCart(@PathVariable Long id) {
        List<Item> items = gatewayItemFeignClient.items();
        Cart cart = new Cart(id, items, LocalDate.now());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping(value = "/editique", produces = { "application/json" })
    public ResponseEntity<List<Property>> editique() {
        String property = "editiqueUrl";
        List<Property> roles = List.of(new Property("@Value",property,editiqueUrl),
                                   new Property("ConfigurationProperties",property,applicationPropertiesConfiguration.getUrl()));
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

}
