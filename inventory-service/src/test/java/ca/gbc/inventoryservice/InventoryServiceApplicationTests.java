package ca.gbc.inventoryservice;

import ca.gbc.inventoryservice.dto.InventoryRequest;
import ca.gbc.inventoryservice.model.Inventory;
import ca.gbc.inventoryservice.repository.InventoryRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class InventoryServiceApplicationTests extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private InventoryRepository inventoryRepository;

    private List<InventoryRequest> getInventoryRequest() {
        InventoryRequest inventoryRequest1 = InventoryRequest.builder()
                .skuCode("sku_12345")
                .quantity(4)
                .build();

        InventoryRequest inventoryRequest2 = InventoryRequest.builder()
                .skuCode("sku_73246")
                .quantity(20)
                .build();

        InventoryRequest inventoryRequest3 = InventoryRequest.builder()
                .skuCode("sku_93427783")
                .quantity(12)
                .build();

        List<InventoryRequest> inventoryRequestList = new ArrayList<>();
        inventoryRequestList.add(inventoryRequest1);
        inventoryRequestList.add(inventoryRequest2);
        inventoryRequestList.add(inventoryRequest3);

        return inventoryRequestList;
    }

    @Test
    void isInStock() throws Exception {
        List<InventoryRequest> inventoryRequestList = getInventoryRequest();
        String inventoryRequestListJsonString = objectMapper.writeValueAsString(inventoryRequestList);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inventoryRequestListJsonString))
                .andExpect(MockMvcResultMatchers.status().isOk());

//        Assertions
        assertEquals(3, inventoryRepository.findAll().size());

//        THEN
        Optional<Inventory> inventories = inventoryRepository.findBySkuCode("sku_12345");
        assertEquals(1L, inventories.stream().count());
    }
}
