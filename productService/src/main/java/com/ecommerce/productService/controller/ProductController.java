package com.ecommerce.productService.controller;

import com.ecommerce.productService.model.ProductRecord;
import com.ecommerce.productService.model.ProductResponse;
import com.ecommerce.productService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Long> addProduct(@RequestBody ProductRecord productRecord) {
        Long productId = productService.getProductId(productRecord);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasAuthority('Admin')||hasAuthority('customer')" +
            "|| hasAuthority('SCOPE_internal')")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long productId) {
        ProductResponse productResponse =
                productService.getProductById(productId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }
//adding preauthorize later
    @PutMapping("/{productId}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable Long productId,
                                               @RequestParam Long quantity) {
        productService.reduceQuantity(productId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //adding preauthorize later

    @PutMapping("/stock/{productId}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Void> reStockQuantity(@PathVariable Long productId,
                                                @RequestParam Long quantity) {
        productService.reStockQuantity(productId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
