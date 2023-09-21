package au.com.name.info.controller;

import au.com.name.info.client.AgifyApiClient;
import au.com.name.info.client.GenderizeApiClient;
import au.com.name.info.client.NationalizeApiClient;
import au.com.name.info.model.AgifyResponse;
import au.com.name.info.model.GenderizeResponse;
import au.com.name.info.model.NationalizeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient
public class PersonInfoControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AgifyApiClient agifyApiClient;

    @MockBean
    private GenderizeApiClient genderizeApiClient;

    @MockBean
    private NationalizeApiClient nationalizeApiClient;

    @Test
    public void shouldGetPersonInfoSuccessfully() {
        //given
        ArrayList<NationalizeResponse.Country> countryList = new ArrayList<>(1);
        NationalizeResponse.Country countryData = new NationalizeResponse.Country("SK", 0.124);
        countryList.add(countryData);

        //when
        when(agifyApiClient.getAge(anyString()))
                .thenReturn(Mono.just(new AgifyResponse(169145, "peter", 68)));
        when(genderizeApiClient.getGender(anyString()))
                .thenReturn(Mono.just(new GenderizeResponse(1094417, "peter", "male", 1.0)));
        when(nationalizeApiClient.getNationality(anyString()))
                .thenReturn(Mono.just(new NationalizeResponse(1104463, "peter", countryList)));

        //then
        webTestClient
                .get()
                .uri("/person/details?name=peter")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.age").isEqualTo(68)
                .jsonPath("$.gender").isEqualTo("male")
                .jsonPath("$.nationality").isArray()
                .jsonPath("$.nationality[0].country_id").isEqualTo("SK")
                .jsonPath("$.nationality[0].probability").isEqualTo(0.124);
    }

    @Test
    public void shouldFailAndThrowBadRequestWhenQueryParamNameIsPassedWIthNUllOrEmptyValue() {
        webTestClient.get()
                .uri("/person/details?name=")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .isEqualTo("name cannot be null or empty");
    }

    @Test
    public void shouldFailAndThrowBadRequestWhenQueryParamNameIsNotPassed() {
        webTestClient.get()
                .uri("/person/details")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .isEqualTo("Required parameter 'name' is not present.");
    }
}
