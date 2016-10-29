package im.bernier.petfinder.datasource;

import im.bernier.petfinder.model.Breeds;
import im.bernier.petfinder.model.Pet;
import im.bernier.petfinder.model.SearchResult;
import im.bernier.petfinder.model.ShelterResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Michael on 2016-07-09.
 */

interface Service {
    @GET("/pet.find?key=d38cdfc784c61ba739980f34d1748ae2&format=xml")
    Call<SearchResult> petFind(@Query("location") String location, @Query("animal") String animal, @Query("breed") String breed, @Query("sex") String sex, @Query("age") String age);

    @GET("/pet.get?key=d38cdfc784c61ba739980f34d1748ae2&format=xml")
    Call<Pet> getPet(@Query("id") String id);

    @GET("/breed.list?key=d38cdfc784c61ba739980f34d1748ae2&format=xml")
    Call<Breeds> getBreeds(@Query("animal") String animal);

    @GET("/shelter.find?key=d38cdfc784c61ba739980f34d1748ae2&format=xml")
    Call<ShelterResult> shelterFind(@Query("location") String location, @Query("name") String name);
}
