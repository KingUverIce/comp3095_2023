package ca.gbc.inventoryservice.service;

import ca.gbc.inventoryservice.dto.InventoryRequest;
import ca.gbc.inventoryservice.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {
    //    boolean isInStock(String skuCode);
    List<InventoryResponse> isInStock(List<InventoryRequest> requestList);
}
