package ca.gbc.inventoryservice;

import ca.gbc.inventoryservice.bootstrap.DataLoader;
import ca.gbc.inventoryservice.dto.InventoryRequest;
import ca.gbc.inventoryservice.dto.InventoryResponse;
import ca.gbc.inventoryservice.model.Inventory;
import ca.gbc.inventoryservice.repository.InventoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class InventoryServiceApplicationTests extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InventoryRepository inventoryRepository;

    private static MockWebServer mockWebServer;


    @BeforeAll
    static void setupServer() throws Exception{
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        System.setProperty("inventory.service.url", "http://localhost:" + mockWebServer.getPort());
    }

    @AfterAll
    static void tearDownServer() throws Exception {
        mockWebServer.shutdown();
    }
    @Test
    void isInStock() throws Exception{

    }

}
