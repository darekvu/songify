package feature;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.songify.SongifyApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SongifyApplication.class)
@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("integration")
class HappyPathIntegrationTest {

    @Container
    static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15-alpine"))
            .withDatabaseName("testing");
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;


    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    }

    @Test
    void canStartPostgresDB() {
        assertThat(postgreSQLContainer.isRunning()).isTrue();
        assertThat(postgreSQLContainer.isCreated()).isTrue();
    }

    @Test
    void f() throws Exception {
//       1 when go to /songs then there no songs returned

        mockMvc.perform(get("/songs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", empty()));

        mockMvc.perform(post("/songs").content(
                        """
                                         {
                                         "name": "Till I collapse",
                                         "releaseDate": "2024-05-27T13:57:36.643Z",
                                         "duration": 20,
                                         "language": "ENGLISH" 
                                         }
                                """.trim()
                ).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.song.id", is(1)))
                .andExpect(jsonPath("$.song.name", is("Till I collapse")))
                .andExpect(jsonPath("$.song.genre.id", is(1)))
                .andExpect(jsonPath("$.song.genre.name", is("default")));

//       2  When I post to /songs "Nobody" then Song "Nobody" is return with id 2
        mockMvc.perform(post("/songs").content("""
                        {
                          "name": "Nobody",
                          "releaseDate": "2024-04-12T13:57:36.643Z",
                          "duration": 20,
                          "language": "ENGLISH"
                        }
                        """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.song.id", is(2)))
                .andExpect(jsonPath("$.song.name", is("Nobody")))
                .andExpect(jsonPath("$.song.genre.id", is(1)))
                .andExpect(jsonPath("$.song.genre.name", is("default")));

//      3     When I go to /genre then i Can see only default genre with id 1
        mockMvc.perform(get("/genre")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.genres[0].id",is(1)))
                .andExpect(jsonPath("$.genres[0].name",is("default")));


// 4       when I post to /genres with Genre "Rap" then "RAP" is return with id 2;
            mockMvc.perform(post("/genre").content("""
                    {
                        "name":"Rap"
                    }
                    """.trim()).contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id",is(2)))
                    .andExpect(jsonPath("$.name",is("Rap")));

//          5.   When I go to /songs/1 then i can see song and  default genre with id 1 and name default
            mockMvc.perform(get("/songs/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.song.genre.id",is(1)))
                    .andExpect(jsonPath("$.song.genre.name",is("default")));

    }
}
