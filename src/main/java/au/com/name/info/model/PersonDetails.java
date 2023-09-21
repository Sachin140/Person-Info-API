package au.com.name.info.model;

import java.util.List;

import static au.com.name.info.model.NationalizeResponse.Country;


public record PersonDetails(int age, String gender, List<Country> nationality) {

}
