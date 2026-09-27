package com.info.api_gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Value("${USER_SERVICE_URL:}")
    private String userServiceUrl;

    @Value("${CATEGORY_SERVICE_URL:}")
    private String categoryServiceUrl;

    @Value("${COURSE_SERVICE_URL:}")
    private String courseServiceUrl;

    @Value("${PURCHASE_SERVICE_URL:}")
    private String purchaseServiceUrl;

    @Value("${EUREKA_ENABLED:true}")
    private boolean eurekaEnabled;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/auth/**")
                        .uri(formatUri(userServiceUrl, 8081, "USER-SERVICE")))
                .route("category-service", r -> r.path("/categories/**")
                        .uri(formatUri(categoryServiceUrl, 8082, "CATEGORY-SERVICE")))
                .route("course-service", r -> r.path("/courses/**")
                        .uri(formatUri(courseServiceUrl, 8084, "COURSE-SERVICE")))
                .route("purchase-service", r -> r.path("/purchases/**")
                        .uri(formatUri(purchaseServiceUrl, 8085, "PURCHASE-SERVICE")))
                .build();
    }

    private String formatUri(String rawUrl, int defaultPort, String serviceName) {
        if (rawUrl == null || rawUrl.isBlank()) {
            return eurekaEnabled ? "lb://" + serviceName : "http://" + serviceName.toLowerCase() + ":" + defaultPort;
        }
        rawUrl = rawUrl.trim();
        // If already formatted with scheme (http://, https://, lb://)
        if (rawUrl.startsWith("http://") || rawUrl.startsWith("https://") || rawUrl.startsWith("lb://")) {
            return rawUrl;
        }
        // If public cloud hostname like learnify-user-service.onrender.com
        if (rawUrl.endsWith(".onrender.com")) {
            return "https://" + rawUrl;
        }
        // If private network service name like learnify-user-service
        return "http://" + rawUrl + ":" + defaultPort;
    }
}
