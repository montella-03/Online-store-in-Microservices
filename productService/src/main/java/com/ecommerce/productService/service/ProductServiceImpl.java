package com.ecommerce.productService.service;

import com.ecommerce.productService.Repository.ProductRepository;
import com.ecommerce.productService.entity.Product;
import com.ecommerce.productService.model.ProductRecord;
import com.ecommerce.productService.model.ProductResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Long getProductId(ProductRecord productRecord) {
        log.info("Adding product..");
        Product product = Product.builder()
                .productName(productRecord.name())
                .price(productRecord.price())
                .quantity(productRecord.quantity())
                .build();
        productRepository.save(product);
        log.info("added product");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        log.info("fetching product..");
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new RuntimeException("product is not found"));
        log.info("fetched product of id",productId);
        return new ProductResponse(product.getProductName(),product.getProductId(),
        product.getPrice(),product.getQuantity());

    }
    //reducing products upon purchase

    @Override
    public void reduceQuantity(Long productId, Long quantity) {
        log.info("reducing quantity");
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new RuntimeException("product with such id not found"));
        if(product.getQuantity()<quantity)
            throw new RuntimeException("No sufficient Quantity");

        product.setQuantity(product.getQuantity()-quantity);
        productRepository.save(product);
        log.info("product quantity reduced");
    }
    //restocking products

    @Override
    public void reStockQuantity(Long productId, Long quantity) {
        log.info("reStocking quantity");
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new RuntimeException("product with such id not found"));

        product.setQuantity(product.getQuantity()+quantity);
        productRepository.save(product);
        log.info("product quantity restocked");

    }
}
