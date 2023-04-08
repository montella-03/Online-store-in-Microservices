package com.microservices.OrderService.service;

import com.microservices.OrderService.Repository.OrderRepository;
import com.microservices.OrderService.entity.Order;
import com.microservices.OrderService.external.client.PaymentService;
import com.microservices.OrderService.external.client.ProductService;
import com.microservices.OrderService.external.request.PaymentRequest;
import com.microservices.OrderService.model.OrderRequest;
import com.microservices.OrderService.model.OrderResponse;
import com.microservices.OrderService.model.ProductDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;
import java.util.regex.Pattern;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private PaymentService paymentService;

    @Override
    public Long placeOrder(OrderRequest orderRequest) {

        //orderEntity->to save data status
        //Product service->to reduce quantity
        //Payment service - > to mark success..else cancel order.

        productService.reduceQuantity(orderRequest.productId(),
                orderRequest.quantity());
        log.info("placing order..",orderRequest);
        Order order = Order.builder()
                .productId(orderRequest.productId())
                .quantity(orderRequest.quantity())
                .orderDate(Instant.now())
                .orderStatus("PLACED")
                .build();
        ProductDetails productDetails = productService.getProductById(orderRequest.productId());
        order.setAmount(productDetails.price()*orderRequest.quantity());
        orderRepository.save(order);
        log.info("paying for products..");

        //regex for generating random codes
        String regex = "^\\d{3}[a-zA-Z]{3}\\d{3}$";
        Pattern pattern = Pattern.compile(regex);

        String code = generateRandomCode(pattern);
        PaymentRequest paymentRequest = new PaymentRequest
                (order.getId(),order.getAmount(),
                        code,orderRequest.paymentMode());
        log.info("order placed successfully");
        String status = null;
        try {
            paymentService.doPayments(paymentRequest);
            log.info("payed successfully..");
            status = "PLACED";
        }
        catch (Exception e){
            log.error("Payment failed...");
            status="PAYMENT_FAILED";

        }
        order.setOrderStatus(status);
        orderRepository.save(order);

        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(Long orderId) {
        log.info("getting order details");
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new RuntimeException("The order with such id not found"));
        log.info("invoking productService details...");
       ProductDetails productDetails = productService.getProductById(order.getProductId());
       log.info("getting payments info...");
       PaymentRequest request = paymentService.getPayment(orderId);

        return new OrderResponse(order.getId(),order.getAmount(),order.getQuantity(),
                order.getOrderDate(),order.getOrderStatus(),new OrderResponse.ProductDetail
                (productDetails.productName(),productDetails.price()),new OrderResponse.PaymentDetails
                (request.mpesaCode(), request.amount()));
    }


    private static String generateRandomCode(Pattern pattern) {
        Random random = new Random();
        String code;
        do {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                builder.append(random.nextInt(10)); // add random digit
            }
            for (int i = 0; i < 3; i++) {
                int ascii = random.nextInt(23) + (random.nextBoolean() ? 33 : 58); // add random uppercase/lowercase letter
                builder.append((char) ascii);
            }
            for (int i = 0; i < 3; i++) {
                builder.append(random.nextInt(10)); // add random digit
            }
            code = builder.toString();
        } while (!pattern.matcher(code).matches()); // repeat until code matches the pattern
        return code;
    }
}
