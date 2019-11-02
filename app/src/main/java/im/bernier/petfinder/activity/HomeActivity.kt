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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mikepenz.aboutlibraries.LibsBuilder
import im.bernier.petfinder.Analytics
import im.bernier.petfinder.R
import im.bernier.petfinder.R.id.*
import im.bernier.petfinder.fragment.PetFragment
import im.bernier.petfinder.fragment.ShelterFragment
import kotlinx.android.synthetic.main.activity_home.*

/**
 * Created by Michael on 2016-10-22.
 */

class HomeActivity : BaseActivity() {

    private val analytics = Analytics

    private lateinit var activeFragment: Fragment

    private lateinit var fm: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        fm = supportFragmentManager

        activeFragment = if (fm.fragments.isEmpty()) {
            val ft = fm.beginTransaction()
            val petFragment = PetFragment.getInstance()
            ft.add(R.id.frameLayoutHomeContent, petFragment, "pet").commit()
            petFragment
        } else {
            fm.findFragmentByTag("pet")!!
        }

        bottomNavigationViewHome.setOnNavigationItemSelectedListener { item ->
            val fragmentTransaction = fm.beginTransaction()
            when (item.itemId) {
                action_search -> {
                    val petFragment = fm.findFragmentByTag("pet") ?: PetFragment.getInstance()
                    if (fm.findFragmentByTag("pet") == null) {
                        fragmentTransaction.hide(activeFragment)
                            .hide(activeFragment)
                            .add(R.id.frameLayoutHomeContent, petFragment, "pet").commit()
                    } else {
                        fragmentTransaction.hide(activeFragment).show(petFragment).commit()
                    }
                    activeFragment = petFragment
                    analytics.track("home_pet_search_click")
                }
                action_shelter -> {
                    val shelterFragment =
                        fm.findFragmentByTag("shelter") ?: ShelterFragment.getInstance()
                    if (fm.findFragmentByTag("shelter") == null) {
                        fragmentTransaction.hide(activeFragment)
                            .hide(activeFragment)
                            .add(R.id.frameLayoutHomeContent, shelterFragment, "shelter")
                            .show(shelterFragment).commit()
                    } else {
                        fragmentTransaction.hide(activeFragment).show(shelterFragment).commit()
                    }
                    activeFragment = shelterFragment
                    analytics.track("home_shelter_search_click")
                }
                action_about -> {
                    val aboutFragment = fm.findFragmentByTag("about") ?: LibsBuilder()
                        .withAboutIconShown(true)
                        .withAboutVersionShown(true)
                        .withAboutDescription(getString(R.string.app_description))
                        .supportFragment()
                    if (fm.findFragmentByTag("about") == null) {
                        fragmentTransaction.hide(activeFragment)
                            .hide(activeFragment)
                            .add(R.id.frameLayoutHomeContent, aboutFragment, "about").commit()
                    } else {
                        fragmentTransaction.hide(activeFragment).show(aboutFragment).commit()
                    }
                    activeFragment = aboutFragment
                    analytics.track("home_about_click")
                }
            }
            true
        }
    }
}
