package com.atguigu.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateWayConfig {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder){
        RouteLocatorBuilder.Builder routes = builder.routes();
        routes.route("path_route_atguigu",
                r -> r.path("/guonei")
                        .uri("http://news.baidu.com/guonei")).build();  // localhost:9527/guonei=>http://news.baidu.com/guonei
        routes.route("path_route_atguigu2",
                r -> r.path("/guoji")
                        .uri("http://news.baidu.com/guoji"));
        routes.route("path_route_atguigu3",
                r -> r.path("/mil")
                        .uri("http://news.baidu.com/mil"));
        routes.route("path_route_atguigu4",
                r -> r.path("/finance")
                        .uri("http://news.baidu.com/finance"));
        return routes.build();
    }

}
