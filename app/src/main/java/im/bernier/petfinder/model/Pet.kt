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
import com.google.firebase.analytics.FirebaseAnalytics
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.util.*

/**
* Created by Michael Bernier on 2016-07-09.
*/

@Root(name = "pet", strict = false)
data class Pet(@field:Element var id: String? = null, @field:Element var name: String? = null, @field:Element var age: String? = null, @field:Element var sex: String? = null, @field:Element(required = false) var description: String? = null, @field:Element(required = false) var media: Media? = null, @field:Element var contact: Contact? = null, @field:ElementList(required = false) var breeds: ArrayList<String> = ArrayList()) {

    val breed: String
        get() {
            val stringBuilder = StringBuilder()
            breeds.indices.forEach { i ->
                stringBuilder.append(breeds[i])
                stringBuilder.append(" ")
            }
            return stringBuilder.toString()
        }

    fun toBundle(): Bundle {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id)
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name)
        bundle.putString("breed", breed)
        bundle.putParcelable("contact", contact?.toBundle())
        return bundle
    }
}
