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

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

/**
 * Created by Michael on 2016-10-29.
 */

@Root(name = "shelter", strict = false)
data class Shelter(@field:Element var id: String? = null, @field:Element var name: String? = null, @field:Element(required = false) var address1: String? = null, @field:Element(required = false) var address2: String? = null, @field:Element var city: String? = null, @field:Element var state: String? = null, @field:Element var country: String? = null, @field:Element var zip: String? = null, @field:Element var latitude: Double = 0.toDouble(), @field:Element var longitude: Double = 0.toDouble(), @field:Element(required = false) var phone: String? = null, @field:Element var email: String? = null)

