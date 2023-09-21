package au.com.name.info.controller;

import au.com.name.info.exception.RequestParamValidationException;
import au.com.name.info.model.PersonDetails;
import au.com.name.info.service.PersonInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.apache.commons.lang3.StringUtils.isBlank;

@RestController
public class PersonInfoController {
    private static final Logger LOG = LoggerFactory.getLogger(PersonInfoController.class);

    private final PersonInfoService personInfoService;

    public PersonInfoController(PersonInfoService personInfoService) {
        this.personInfoService = personInfoService;
    }

    @GetMapping(value = "/person/details")
    public Mono<PersonDetails> getPersonDetails(@RequestParam String name) {
        LOG.info("Received request to get person details with name: [{}]", name);
        if (isBlank(name)) {
            throw new RequestParamValidationException("name cannot be null or empty");
        }
        return personInfoService.getRandomPersonInfo(name);
    }
}
