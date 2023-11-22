package copito.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.context.annotation.Bean;

import java.util.Map;

@SpringBootApplication
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    @Bean
    public EurekaClientConfigBean eurekaInstanceConfig(InetUtils inetUtils){
        var config = new EurekaClientConfigBean();
        config.setServiceUrl(Map.of("defaultZine", "http://localhost:8761/eureka"));
        return config;
    }
}
