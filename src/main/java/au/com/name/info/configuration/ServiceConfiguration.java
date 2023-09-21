package au.com.name.info.configuration;

import au.com.name.info.client.AgifyApiClient;
import au.com.name.info.client.GenderizeApiClient;
import au.com.name.info.client.NationalizeApiClient;
import au.com.name.info.service.PersonInfoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public PersonInfoService personInfoService(AgifyApiClient agifyApiClient, GenderizeApiClient genderizeApiClient,
                                                NationalizeApiClient nationalizeApiClient) {
        return new PersonInfoService(agifyApiClient, genderizeApiClient, nationalizeApiClient);
    }
}
