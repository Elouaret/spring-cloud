package ch.ge.apside.archi.gateway.controller;

//import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/circuitbreakerfallback")
public class RouteController {

    @RequestMapping("/")
    public String circuitbreakerfallback() {
        return "This is a fallback";
    }
}
