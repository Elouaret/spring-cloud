package ch.ge.apside.archi.cart.service.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

//@Data
//@Component
//@ConfigurationProperties("app.conf")
//@RefreshScope
public class ApplicationPropertiesConfiguration
{
    private int valeur;
}
