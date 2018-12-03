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

package im.bernier.petfinder.activity

import android.os.Bundle
import android.util.SparseArray
import android.view.ViewGroup
import com.mikepenz.aboutlibraries.LibsBuilder
import im.bernier.petfinder.Analytics
import im.bernier.petfinder.R
import im.bernier.petfinder.R.id.*
import im.bernier.petfinder.view.PetSearchViewTab
import im.bernier.petfinder.view.ShelterSearchViewTab
import kotlinx.android.synthetic.main.activity_home.*

/**
 * Created by Michael on 2016-10-22.
 */

class HomeActivity : BaseActivity() {

    private lateinit var viewGroups: SparseArray<ViewGroup>
    private val analytics = Analytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewGroups = SparseArray()
        viewGroups.append(action_search, PetSearchViewTab(this@HomeActivity))
        frameLayoutHomeContent.addView(viewGroups.get(action_search))

        bottomNavigationViewHome.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                action_search -> {
                    frameLayoutHomeContent.removeAllViews()
                    if (viewGroups.get(action_search) == null) {
                        viewGroups.append(action_search, PetSearchViewTab(this@HomeActivity))
                    }
                    frameLayoutHomeContent.addView(viewGroups.get(action_search))
                    frameLayoutHomeContent.requestLayout()

                    analytics.track("home_pet_search_click")
                }
                action_shelter -> {
                    frameLayoutHomeContent.removeAllViews()
                    if (viewGroups.get(action_shelter) == null) {
                        viewGroups.append(action_shelter, ShelterSearchViewTab(this@HomeActivity))
                    }
                    frameLayoutHomeContent.addView(viewGroups.get(action_shelter))
                    frameLayoutHomeContent.requestLayout()
                    analytics.track("home_shelter_search_click")
                }
                action_about -> {
                    frameLayoutHomeContent.removeAllViews()
                    val fragment = LibsBuilder()
                            .withAboutIconShown(true)
                            .withAboutVersionShown(true)
                            .withAboutDescription(getString(R.string.app_description))
                            .supportFragment()

                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.add(R.id.frameLayoutHomeContent, fragment).commit()
                    analytics.track("home_about_click")
                }
            }
            true
        }
    }
}
