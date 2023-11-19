package ca.gbc.orderservice.controller;

import ca.gbc.orderservice.dto.OrderRequest;
import ca.gbc.orderservice.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderServiceImpl orderService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        return "Order placed successfully";
    }
}
