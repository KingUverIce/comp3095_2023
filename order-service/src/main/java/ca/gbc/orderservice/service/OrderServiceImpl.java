package ca.gbc.orderservice.service;

import ca.gbc.orderservice.dto.InventoryRequest;
import ca.gbc.orderservice.dto.InventoryResponse;
import ca.gbc.orderservice.dto.OrderLineItemDto;
import ca.gbc.orderservice.dto.OrderRequest;
import ca.gbc.orderservice.model.Order;
import ca.gbc.orderservice.model.OrderLineItem;
import ca.gbc.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClient;

    @Value("${inventory.service.url}")
    private String inventoryApiUri;

    @Override
    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItemList = orderRequest
                .getOrderLineItemDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemList(orderLineItemList);

        List<InventoryRequest> inventoryRequestList = order.getOrderLineItemList()
                .stream()
                .map(orderLineItem -> InventoryRequest.builder()
                        .skuCode(orderLineItem.getSkuCode())
                        .quantity(orderLineItem.getQuantity())
                        .build())
                .toList();

        // making a request to other microservices
        List<InventoryResponse> inventoryResponseList = webClient.build()
                .post()
                .uri(inventoryApiUri)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(inventoryRequestList)
                .retrieve()
                .bodyToFlux(InventoryResponse.class)
                .collectList()
//                block to make this synchronous
                .block();

        assert inventoryResponseList != null;
        boolean allProductInStock = inventoryResponseList
                .stream()
                .allMatch(InventoryResponse::isSufficientStock);

        if (Boolean.TRUE.equals(allProductInStock)) {
            return orderRepository.save(order).getOrderNumber();
        }
        throw new RuntimeException("Not all products are in stock, order cannot be placed");
    }

    private OrderLineItem mapToDto(OrderLineItemDto orderLineItemDto) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(orderLineItemDto.getPrice());
        orderLineItem.setQuantity(orderLineItemDto.getQuantity());
        orderLineItem.setSkuCode(orderLineItemDto.getSkuCode());

        return orderLineItem;
    }
}
