package au.com.name.info.service;

import au.com.name.info.client.AgifyApiClient;
import au.com.name.info.client.GenderizeApiClient;
import au.com.name.info.client.NationalizeApiClient;
import au.com.name.info.model.AgifyResponse;
import au.com.name.info.model.GenderizeResponse;
import au.com.name.info.model.NationalizeResponse;
import au.com.name.info.model.PersonDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class PersonInfoService {

    private static final Logger LOG = LoggerFactory.getLogger(PersonInfoService.class);

    private final AgifyApiClient agifyApiClient;
    private final GenderizeApiClient genderizeApiClient;
    private final NationalizeApiClient nationalizeApiClient;

    public PersonInfoService(AgifyApiClient agifyApiClient, GenderizeApiClient genderizeApiClient, NationalizeApiClient nationalizeApiClient) {
        this.agifyApiClient = agifyApiClient;
        this.genderizeApiClient = genderizeApiClient;
        this.nationalizeApiClient = nationalizeApiClient;
    }

    /**
     * This method is responsible for calling Public APIs to get random person details like age, gender and nationality.
     *
     * @param name name
     * @return PersonDetails
     */

    public Mono<PersonDetails> getRandomPersonInfo(String name) {
        LOG.info("Calling external API to get random person details..");
        try {
            Mono<AgifyResponse> agifyResponse = agifyApiClient.getAge(name);
            Mono<GenderizeResponse> genderizeResponse = genderizeApiClient.getGender(name);
            Mono<NationalizeResponse> nationalizeResponse = nationalizeApiClient.getNationality(name);

            return Mono.zip(agifyResponse, genderizeResponse, nationalizeResponse)
                    .map(response -> {
                        AgifyResponse age = response.getT1();
                        LOG.info("Received response for person Age details: {}", age);
                        GenderizeResponse gender = response.getT2();
                        LOG.info("Received response for person Gender details: {}", gender);
                        NationalizeResponse nationality = response.getT3();
                        LOG.info("Received response for person Nationality details: {}", nationality);
                        PersonDetails personDetails = new PersonDetails(age.age(), gender.gender(), nationality.country());
                        LOG.info("Returning final API response:: {}", personDetails);
                        return personDetails;
                    })
                    .onErrorMap(throwable -> {
                        LOG.error("Error occurred while fetching person details: {}", throwable.getMessage());
                        return new ResponseStatusException(INTERNAL_SERVER_ERROR, "Internal Server Error");
                    });
        } catch (Exception e) {
            LOG.error("Error occurred outside the context: {}", e.getMessage());
            return Mono.error(new ResponseStatusException(INTERNAL_SERVER_ERROR, "Internal Server Error"));
        }
    }
}
