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

import im.bernier.petfinder.model.Pet
import im.bernier.petfinder.model.Search
import im.bernier.petfinder.model.ShelterSearch

/**
 * Created by Michael on 2016-07-09.
 */

class Storage private constructor() {

    private object Holder { val INSTANCE = Storage() }

    var pet: Pet? = null
    var search: Search? = null
    var shelterSearch: ShelterSearch? = null

    companion object {
        val instance: Storage by lazy { Holder.INSTANCE }
    }
}
