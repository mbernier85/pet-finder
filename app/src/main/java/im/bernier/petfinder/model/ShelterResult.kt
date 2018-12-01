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

import org.simpleframework.xml.*
import java.util.*

/**
 * Created by Michael on 2016-10-29.
 */

@Root(name = "petfinder", strict = false)
data class ShelterResult(@field:Attribute @field:Namespace(reference = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi") private var noNamespaceSchemaLocation: String = "", @field:ElementList(required = false) var shelters: ArrayList<Shelter>? = null, @field:Element var header: ErrorHeader? = null)
