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

import im.bernier.petfinder.model.Breeds
import im.bernier.petfinder.model.Pet
import im.bernier.petfinder.model.SearchResult
import im.bernier.petfinder.model.ShelterResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Michael on 2016-07-09.
 */

internal interface Service {
    @GET("/pet.find?key=d38cdfc784c61ba739980f34d1748ae2&format=xml")
    fun petFind(
        @Query("location") location: String?,
        @Query("animal") animal: String?,
        @Query("breed") breed: String?,
        @Query("sex") sex: String?, @Query("age") age: String?
    ): Call<SearchResult>

    @GET("/pet.get?key=d38cdfc784c61ba739980f34d1748ae2&format=xml")
    fun getPet(@Query("id") id: String): Call<Pet>

    @GET("/breed.list?key=d38cdfc784c61ba739980f34d1748ae2&format=xml")
    fun getBreeds(@Query("animal") animal: String): Call<Breeds>

    @GET("/shelter.find?key=d38cdfc784c61ba739980f34d1748ae2&format=xml")
    fun shelterFind(
        @Query("location") location: String,
        @Query("name") name: String
    ): Call<ShelterResult>
}
