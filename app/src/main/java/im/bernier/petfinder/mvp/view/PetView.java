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

package im.bernier.petfinder.mvp.view;

import im.bernier.petfinder.model.Pet;

/**
 * Created by Michael on 2016-07-09.
 */

public interface PetView {
    void updateUi(Pet pet);
    void openImageViewer(Pet pet);
    void openEmail(Pet pet);
    void openDialer(Pet pet);
    void openMap(Pet pet);
    void doFinish();
}
