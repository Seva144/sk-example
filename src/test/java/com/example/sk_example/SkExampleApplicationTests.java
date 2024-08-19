package com.example.sk_example;

import com.example.sk_example.dto.CurrentDto;
import com.example.sk_example.dto.SkDto;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Sql({"/schema.sql"})
class SkExampleApplicationTests {

    @Autowired
    private SkDao skDao;

    @LocalServerPort
    private Integer port;

    @Container
    @ServiceConnection
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.12"));

    @Test
    void test_add_current_success() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:" + port + "/modify";
        SkDto skDto = new SkDto(1, 3);
        HttpEntity<SkDto> request = new HttpEntity<>(skDto);

        ResponseEntity<String> response = restTemplate.exchange(
                resourceUrl,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {
                }
        );
        assertEquals(200, HttpStatus.SC_OK);
        assertEquals("{\"current\":3}", response.getBody());
    }

    @Test
    void test_repository() {
        assertEquals(3, skDao.increaseCurrentById(new SkDto(1, 3)));
    }

    @Test
    void test_repository_failure() {
        assertNull(skDao.increaseCurrentById(new SkDto(2, 3)));
    }

    @Test
    void test_service() {
        SkService skService = new SkService(skDao);
        assertEquals(new CurrentDto(3), skService.returnCurrent(new SkDto(1, 3)));
    }

    @Test
    void test_service_failure() {
        SkService skService = new SkService(skDao);
        assertNull(skService.returnCurrent(new SkDto(2, 3)));
    }

}
