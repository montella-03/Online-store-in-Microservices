package com.microservices.OrderService.external.client;

import com.microservices.OrderService.model.OrderResponse;
import com.microservices.OrderService.model.ProductDetails;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CircuitBreaker(name = "circuit")
@FeignClient(name = "ProductService/product")
public interface ProductService {
    @PutMapping("/{productId}")
    ResponseEntity<Void> reduceQuantity(@PathVariable Long productId,
                                               @RequestParam Long quantity);

    @GetMapping("/{productId}")
    public ProductDetails getProductById
            (@PathVariable Long productId);


}
