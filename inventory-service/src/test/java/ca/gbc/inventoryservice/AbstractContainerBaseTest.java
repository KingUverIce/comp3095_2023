package ca.gbc.inventoryservice;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
public abstract class AbstractContainerBaseTest {
    //postgresql container
    static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER;

    static {

        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
        POSTGRE_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
        dynamicPropertyRegistry.add("spring.datasource.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", POSTGRE_SQL_CONTAINER::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", POSTGRE_SQL_CONTAINER::getPassword);
    }

}
