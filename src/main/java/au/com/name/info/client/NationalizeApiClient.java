package au.com.name.info.client;

import au.com.name.info.model.NationalizeResponse;
import reactor.core.publisher.Mono;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NationalizeApiClient {

    @GET("/")
    Mono<NationalizeResponse> getNationality(@Query("name") String name);
}
