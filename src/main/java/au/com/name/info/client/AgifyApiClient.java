package au.com.name.info.client;

import au.com.name.info.model.AgifyResponse;
import reactor.core.publisher.Mono;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AgifyApiClient {

    @GET("/")
    Mono<AgifyResponse> getAge(@Query("name") String name);
}

