package com.microservices.Gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class GatewayController {
    @GetMapping("/orderServiceFallBack")
    public String orderServiceFallBack(){
        return "orderService is down!";
    }
    @GetMapping("/productServiceFallBack")
    public String productServiceFallBack(){
        return "productService is down!";
    }
    @GetMapping("/paymentServiceFallBack")
    public String paymentServiceFallBack(){
        return "paymentService is down!";
    }
}
