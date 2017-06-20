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

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

import java.lang.reflect.Array
import java.util.ArrayList

/**
 * Created by Michael on 2016-07-09.
 */

@Root(name = "media", strict = false)
data class Media(@field:ElementList(required = false) var photos: ArrayList<Photo>? = null) {

    val hiResPhotos: ArrayList<Photo>
        get() {
            val hiRes = ArrayList<Photo>()
            photos?.filterTo(hiRes) { it.size.orEmpty().equals("x", ignoreCase = true) }
            return hiRes
        }

    val thumbnail: String?
        get() {
            for ((_, size, value) in photos.orEmpty()) {
                if (size.orEmpty().equals("pn", ignoreCase = true)) {
                    return value
                }
            }
            return null
        }
}
