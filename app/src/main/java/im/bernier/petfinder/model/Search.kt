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

package im.bernier.petfinder.model

import android.os.Bundle

/**
 * Created by Michael on 2016-07-13.
 */

data class Search(var location: String? = "", var animal: Animal? = Animal(), var breed: String? = "", var age: String? = "", var sex: String? = "") {

    val bundle: Bundle
        get() {
            val bundle = Bundle()
            bundle.putString("pet_search_location", location)
            bundle.putString("pet_search_animal", if (animal != null) animal!!.name else null)
            bundle.putString("pet_search_breed", breed)
            bundle.putString("pet_search_age", age)
            bundle.putString("pet_search_sex", sex)
            return bundle
        }
}
