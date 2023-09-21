package au.com.name.info.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "random-info-api")
public class Properties {

    private String ageApiBaseUrl;
    private String genderApiBaseUrl;
    private String nationalityApiBaseUrl;

    public String getAgeApiBaseUrl() {
        return ageApiBaseUrl;
    }

    public void setAgeApiBaseUrl(String ageApiBaseUrl) {
        this.ageApiBaseUrl = ageApiBaseUrl;
    }

    public String getGenderApiBaseUrl() {
        return genderApiBaseUrl;
    }

    public void setGenderApiBaseUrl(String genderApiBaseUrl) {
        this.genderApiBaseUrl = genderApiBaseUrl;
    }

    public String getNationalityApiBaseUrl() {
        return nationalityApiBaseUrl;
    }

    public void setNationalityApiBaseUrl(String nationalityApiBaseUrl) {
        this.nationalityApiBaseUrl = nationalityApiBaseUrl;
    }
}
