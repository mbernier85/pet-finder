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
 * Created by micha on 2016-08-08.
 */

@Root(name = "status", strict = false)
data class ErrorStatus(@field:Element(required = false) var message: String? = null, @field:Element var code: Int = 0)
