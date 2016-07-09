package im.bernier.petfinder.datasource;

import im.bernier.petfinder.model.SearchResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Michael on 2016-07-09.
 */

public interface Service {
    @GET("/pet.find?key=d38cdfc784c61ba739980f34d1748ae2&format=xml&animal=dog")
    Call<SearchResult> petFind(@Query("location") String location);
}
