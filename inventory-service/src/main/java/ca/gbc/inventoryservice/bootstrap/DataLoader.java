package ca.gbc.inventoryservice.bootstrap;

import ca.gbc.inventoryservice.model.Inventory;
import ca.gbc.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// stereotype
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final InventoryRepository inventoryRepository;

    //    runs the app when booting up
    @Override
    public void run(String... args) throws Exception {
//        we can seed the database while booting up
        if (inventoryRepository.findBySkuCode("sku_12345").isEmpty()) {
            Inventory widgets = Inventory.builder()
                    .skuCode("sku_12345")
                    .quantity(5)
                    .build();

            inventoryRepository.save(widgets);
        }
        if (inventoryRepository.findBySkuCode("sku_73246").isEmpty()) {
            Inventory widgets = Inventory.builder()
                    .skuCode("sku_73246")
                    .quantity(10)
                    .build();

            inventoryRepository.save(widgets);
        }
        if (inventoryRepository.findBySkuCode("sku_93427783").isEmpty()) {
            Inventory widgets = Inventory.builder()
                    .skuCode("sku_93427783")
                    .quantity(15)
                    .build();

            inventoryRepository.save(widgets);
        }
    }
}
