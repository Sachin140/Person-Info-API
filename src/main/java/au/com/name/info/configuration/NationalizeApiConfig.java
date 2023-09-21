package au.com.name.info.configuration;

import au.com.name.info.client.NationalizeApiClient;
import com.jakewharton.retrofit2.adapter.reactor.ReactorCallAdapterFactory;
import okhttp3.OkHttpClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
@EnableConfigurationProperties(Properties.class)
public class NationalizeApiConfig {

    @Bean
    public Retrofit nationalizeApiRetrofit(Properties properties) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        return new Retrofit.Builder()
                .baseUrl(properties.getNationalityApiBaseUrl())
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(ReactorCallAdapterFactory.create())
                .build();
    }

    @Bean
    public NationalizeApiClient nationalizeApiClient(Retrofit nationalizeApiRetrofit) {
        return nationalizeApiRetrofit.create(NationalizeApiClient.class);
    }
}
