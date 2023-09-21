package au.com.name.info.service;

import au.com.name.info.client.AgifyApiClient;
import au.com.name.info.client.GenderizeApiClient;
import au.com.name.info.client.NationalizeApiClient;
import au.com.name.info.model.AgifyResponse;
import au.com.name.info.model.GenderizeResponse;
import au.com.name.info.model.NationalizeResponse;
import au.com.name.info.model.NationalizeResponse.Country;
import au.com.name.info.model.PersonDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class PersonInfoServiceTest {

    @InjectMocks
    private PersonInfoService apiService;

    @Mock
    private AgifyApiClient agifyApiClient;

    @Mock
    private GenderizeApiClient genderizeApiClient;

    @Mock
    private NationalizeApiClient nationalizeApiClient;

    @BeforeEach
    public void setup() {
        openMocks(this);
        apiService = new PersonInfoService(agifyApiClient, genderizeApiClient, nationalizeApiClient);
    }

    @Test
    public void testGetPersonDetails() {
        //given  - Create sample responses
        ArrayList<Country> countryList = new ArrayList<>(1);
        Country countryData = new Country("SK", 1.2);
        countryList.add(countryData);

        AgifyResponse agifyResponse = new AgifyResponse(169145, "John", 42);
        GenderizeResponse genderizeResponse = new GenderizeResponse(1094417, "John", "male", 1.0);
        NationalizeResponse nationalizeResponse = new NationalizeResponse(1104463, "John", countryList);

        // Mock responses from the external APIs
        when(agifyApiClient.getAge(anyString())).thenReturn(Mono.just(agifyResponse));
        when(genderizeApiClient.getGender(anyString())).thenReturn(Mono.just(genderizeResponse));
        when(nationalizeApiClient.getNationality(anyString())).thenReturn(Mono.just(nationalizeResponse));

        //when - Call service method
        Mono<PersonDetails> result = apiService.getRandomPersonInfo("John");

        //then - Verify that the external APIs were called with the correct parameters
        verify(agifyApiClient).getAge("John");
        verify(genderizeApiClient).getGender("John");
        verify(nationalizeApiClient).getNationality("John");

        // Assert the expected response
        StepVerifier.create(result)
                .expectNextMatches(info -> {
                    // Check if the response matches the expected values
                    assertEquals(agifyResponse.age(), info.age());
                    assertEquals(genderizeResponse.gender(), info.gender());
                    assertEquals(nationalizeResponse.country().get(0).country_id(), info.nationality().get(0).country_id());
                    return true;
                })
                .expectComplete()
                .verify();
    }
}
