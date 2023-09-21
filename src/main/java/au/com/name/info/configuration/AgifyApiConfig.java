package au.com.name.info.configuration;

import au.com.name.info.client.AgifyApiClient;
import com.jakewharton.retrofit2.adapter.reactor.ReactorCallAdapterFactory;
import okhttp3.OkHttpClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
@EnableConfigurationProperties(Properties.class)
public class AgifyApiConfig {

    @Bean
    public Retrofit agifyApiRetrofit(Properties properties) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        return new Retrofit.Builder()
                .baseUrl(properties.getAgeApiBaseUrl())
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(ReactorCallAdapterFactory.create())
                .build();
    }

    @Bean
    public AgifyApiClient agifyApiClient(Retrofit agifyApiRetrofit) {
        return agifyApiRetrofit.create(AgifyApiClient.class);
    }
}
