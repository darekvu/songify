package feature;


import com.songify.SongifyApplication;
import org.junit.jupiter.api.Test;
import org.springdoc.webmvc.core.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(classes = SongifyApplication.class)
@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("integration")
class HappyPathIntegrationTest {

    @Container
    static final PostgreSQLContainer<?> postgreSQLContainer= new PostgreSQLContainer<>(DockerImageName.parse("postgres:15-alpine"))
            .withDatabaseName("testing");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RequestService requestBuilder;

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",postgreSQLContainer::getJdbcUrl);
    }

    @Test
    void canStartPostgresDB() {
        //given
        assertThat(postgreSQLContainer.isRunning()).isTrue();
        assertThat(postgreSQLContainer.isCreated()).isTrue();
        //when
        //then
    }

    @Test
    void f() throws Exception {
        mockMvc.perform(get("/songs")
                .contentType(MediaType.APPLICATION_JSON));
    }
}
