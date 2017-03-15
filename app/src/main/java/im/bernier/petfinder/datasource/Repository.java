/*
 *   This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * If it is not possible or desirable to put the notice in a particular
 * file, then You may include the notice in a location (such as a LICENSE
 * file in a relevant directory) where a recipient would be likely to look
 * for such a notice.
 *
 * You may add additional accurate notices of copyright ownership.
 */

package im.bernier.petfinder.datasource;

import im.bernier.petfinder.model.Breeds;
import im.bernier.petfinder.model.Search;
import im.bernier.petfinder.model.SearchResult;
import im.bernier.petfinder.model.ShelterResult;
import im.bernier.petfinder.model.ShelterSearch;
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

    public Call<SearchResult> petFind(Search search) {
        return service.petFind(search.getLocation(), search.getAnimal() != null ? search.getAnimal().getKey() : null, search.getBreed(), search.getSex(), search.getAge());
    }

    public Call<Breeds> loadBreeds(String animal) {
        return service.getBreeds(animal);
    }

    public Call<ShelterResult> shelterFind(ShelterSearch shelterSearch) {
        return service.shelterFind(shelterSearch.getLocation(), shelterSearch.getName().isEmpty() ? null : shelterSearch.getName());
    }

}
