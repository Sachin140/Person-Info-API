package au.com.name.info.client;

import au.com.name.info.model.GenderizeResponse;
import reactor.core.publisher.Mono;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GenderizeApiClient {

    @GET("/")
    Mono<GenderizeResponse> getGender(@Query("name") String name);
}
