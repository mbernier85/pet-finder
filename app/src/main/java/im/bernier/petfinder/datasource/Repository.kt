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

package im.bernier.petfinder.datasource

import im.bernier.petfinder.model.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

/**
 * Created by Michael on 2016-07-09.
 */

object Repository {

    private val service: Service

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.petfinder.com")
            .client(client)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
        service = retrofit.create(Service::class.java)
    }

    fun petFind(search: Search): Call<SearchResult> {
        return service.petFind(
            search.location,
            search.animal?.key,
            search.breed,
            search.sex,
            search.age
        )
    }

    fun loadBreeds(animal: String): Call<Breeds> {
        return service.getBreeds(animal)
    }

    fun shelterFind(shelterSearch: ShelterSearch): Call<ShelterResult> {
        return service.shelterFind(shelterSearch.location!!, shelterSearch.name!!)
    }
}
