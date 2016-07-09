package im.bernier.petfinder.datasource;

import im.bernier.petfinder.model.SearchResult;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by Michael on 2016-07-09.
 */

public class Repository {

    private static Repository instance;
    private Service service;

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    private Repository() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.petfinder.com")
                .client(client)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        service = retrofit.create(Service.class);
    }

    public Call<SearchResult> petFind(String location) {
        return service.petFind(location);
    }

}
