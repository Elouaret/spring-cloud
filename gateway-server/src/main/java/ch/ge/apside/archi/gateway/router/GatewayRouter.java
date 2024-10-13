package ch.ge.apside.archi.gateway.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRouter {

//    @Value("${cart.service.name}")
    private String cartServiceName;
//
//    @Value("${item.service.name}")
    private String itemServiceName;

//    @Autowired
//    private ServiceIdentifier serviceIdentifier;

//    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {

        return builder.routes()
                .route("r1", r -> r.path("/item/**")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
//                        .uri(serviceIdentifier.getItemUri() + "/item"))
                        .uri("lb://" + itemServiceName))
                .route("r2", r -> r.path("/cart/**")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
//                        .uri(serviceIdentifier.getCartUri() + "/cart/"))
//                         .uri("http://localhost:8100/"))
                        .uri("lb://" + cartServiceName))
//                .route(p -> p
//                        .host("*.circuitbreaker.com")
//                        .filters(f -> f
//                                .circuitBreaker(config -> config
//                                        .setName("mycmd")
//                                        .setFallbackUri("forward:/fallback")))
//                        .uri("circuitbreakerfallback"))
                .build();
    }

//    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        //@formatter:off
        return builder.routes()
                .route(p -> p
                        .path("/get-item/**")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("http://localhost:8200"))
                .route(p -> p
                        .path("/get-cart")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("http://localhost:8100/"))
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

//    @Bean
//    RedisRateLimiter redisRateLimiter() {
//        return new RedisRateLimiter(1, 2);
//    }

//    @Bean
//    public DiscoveryClientRouteDefinitionLocator discoveryClientRouteLocator(ReactiveDiscoveryClient discoveryClient, DiscoveryLocatorProperties properties) {
//        return new DiscoveryClientRouteDefinitionLocator(discoveryClient, properties);
//    }
}
