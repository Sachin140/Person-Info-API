package au.com.name.info.model;

import java.util.ArrayList;


public record NationalizeResponse(int count, String name, ArrayList<Country> country) {

    public record Country(String country_id, double probability) {
    }

}
