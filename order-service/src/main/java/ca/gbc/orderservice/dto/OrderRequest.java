package ca.gbc.orderservice.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private List<OrderLineItemDto> orderLineItemDtoList =
            new ArrayList<OrderLineItemDto>();
}
