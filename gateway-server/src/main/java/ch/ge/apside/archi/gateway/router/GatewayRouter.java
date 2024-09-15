package ch.ge.apside.archi.gateway.router;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRouter {

    @Value("${cart.service.name}")
    private String cartServiceName;

    @Value("${item.service.name}")
    private String itemServiceName;


    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(p -> p
                        .path("/item")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri(itemServiceUrl))
                .route(p -> p
                        .path("/cart")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri(cartServiceUrl))
//                .route(p -> p
//                        .host("*.circuitbreaker.com")
//                        .filters(f -> f
//                                .circuitBreaker(config -> config
//                                        .setName("mycmd")
//                                        .setFallbackUri("forward:/fallback")))
//                        .uri(httpUri))
                .build();
    }
    /*
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        //@formatter:off
        return builder.routes()
                .route(p -> p
                        .path("/get-item")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("http://localhost:8081/item/"))
                .route(p -> p
                        .path("/get-cart")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("http://localhost:8082/cart/"))
//                .route("host_route", r -> r.host("*.myhost.org")
//                        .uri("http://httpbin.org"))
//                .route("rewrite_route", r -> r.host("*.rewrite.org")
//                        .filters(f -> f.rewritePath("/foo/(?<segment>.*)",
//                                "/${segment}"))
//                        .uri("http://httpbin.org"))
//                .route("circuitbreaker_route", r -> r.host("*.circuitbreaker.org")
//                        .filters(f -> f.circuitBreaker(c -> c.setName("slowcmd")))
//                        .uri("http://httpbin.org"))
//                .route("circuitbreaker_fallback_route", r -> r.host("*.circuitbreakerfallback.org")
//                        .filters(f -> f.circuitBreaker(c -> c.setName("slowcmd").setFallbackUri("forward:/circuitbreakerfallback")))
//                        .uri("http://httpbin.org"))
//                .route("limit_route", r -> r
//                        .host("*.limited.org").and().path("/anything/**")
//                        .filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())))
//                        .uri("http://httpbin.org"))
//                .route("websocket_route", r -> r.path("/echo")
//                        .uri("ws://localhost:9000"))
                .build();
        //@formatter:on
    }
*/
//    @Bean
//    RedisRateLimiter redisRateLimiter() {
//        return new RedisRateLimiter(1, 2);
//    }
}
