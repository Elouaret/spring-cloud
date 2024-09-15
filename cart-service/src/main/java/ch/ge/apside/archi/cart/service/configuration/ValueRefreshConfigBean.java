package ch.ge.apside.archi.cart.service.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class ValueRefreshConfigBean {
    private String role;

    public ValueRefreshConfigBean(@Value("${user.role}") String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
