package ch.ge.apside.archi.cart.service.controller.rest;

import ch.ge.apside.archi.cart.service.configuration.ApplicationPropertiesConfiguration;
import ch.ge.apside.archi.cart.service.configuration.ValueRefreshConfigBean;
import ch.ge.apside.archi.cart.service.controller.client.GatewayItemFeignClient;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
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
//@RefreshScope
//@ConfigurationProperties
public class CartRestController {

    /********************* FEIGN CLIENT *********************/

    // http://localhost:8090/cart-service/cart/itemsFeign

    @Autowired
    private GatewayItemFeignClient gatewayItemFeignClient;

    @GetMapping("/itemsFeign")
    public String itemsFeign(){
        return gatewayItemFeignClient.items();
    }

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

    @GetMapping("/list")
    public String list() {
        String list = """
                {
                  id: 1,
                  name: Panier 1
                  price: 10000€
                },
                {
                  id: 2,
                  name: Panier 2
                  price: 5000000€
                }
                """;
        return list;
    }

    @Autowired
    private ApplicationPropertiesConfiguration applicationPropertiesConfiguration;

    @Autowired
    private ValueRefreshConfigBean valueRefreshConfigBean;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${user.role}")
    private String role;
//
//    @GetMapping("/")
//    public String index() {
//        return String.format("Index from '%s'!", eurekaClient.getApplication(appName).getName());
//    }
//
    @GetMapping("/name")
    public String hello() {
        return String.format("Name from '%s'!", eurekaClient.getApplication(appName).getName());
    }

    @GetMapping("/role")
    public String role() {
        StringBuilder sb = new StringBuilder();
        sb.append("CartRestController. @Value Role:  : " + role);
        sb.append("\n");
        sb.append("CartRestController. applicationPropertiesConfiguration @Value Role:  : " + applicationPropertiesConfiguration.getRole());
        sb.append("\n");
        sb.append("CartRestController. valueRefreshConfigBean @Value Role:  : " + valueRefreshConfigBean.getRole());
        return sb.toString();
    }

}
