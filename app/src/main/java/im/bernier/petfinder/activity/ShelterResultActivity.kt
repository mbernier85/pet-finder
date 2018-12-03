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

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import im.bernier.petfinder.R
import im.bernier.petfinder.adapter.ShelterAdapter
import im.bernier.petfinder.model.Shelter
import im.bernier.petfinder.mvp.presenter.ShelterResultPresenter
import im.bernier.petfinder.mvp.view.ShelterResultView
import kotlinx.android.synthetic.main.activity_shelter_result.*

/**
 * Created by Michael on 2016-10-30.
 */

class ShelterResultActivity : BaseActivity(), ShelterResultView {

    lateinit var presenter: ShelterResultPresenter
    lateinit var adapter: ShelterAdapter

    override fun doFinish() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shelter_result)

        if (isTablet) {
            recyclerViewShelter.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        } else {
            recyclerViewShelter.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }
        adapter = ShelterAdapter(object : ShelterAdapter.ShelterItemListener {
            override fun itemClick(shelter: Shelter) {

            }

            override fun phoneClick(shelter: Shelter) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse(String.format("tel:%s", shelter.phone))
                startActivity(intent)
            }

            override fun emailClick(shelter: Shelter) {
                val emailIntent = Intent(android.content.Intent.ACTION_SEND)

                emailIntent.type = "plain/text"
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, arrayOf(shelter.email!!))

                startActivity(Intent.createChooser(emailIntent, getString(R.string.send_mail)))
            }

            override fun directionClick(shelter: Shelter) {
                val intent = Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(String.format("http://maps.google.com/maps?daddr=%s, %s", shelter.city, shelter.zip)))
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity")
                startActivity(intent)
            }
        })
        recyclerViewShelter.adapter = adapter

        presenter = ShelterResultPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.onAttach()
    }

    override fun onStop() {
        presenter.onDetach()
        super.onStop()
    }

    override fun showError(error: String) {
        com.google.android.material.snackbar.Snackbar.make(recyclerViewShelter, error, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).show()
        progressBarShelter.visibility = View.GONE
    }

    override fun showProgress() {
        progressBarShelter.visibility = View.VISIBLE
        recyclerViewShelter.visibility = View.GONE
        textViewShelterMessage.visibility = View.GONE
    }

    override fun showResults(shelters: List<Shelter>) {
        progressBarShelter.visibility = View.GONE
        adapter.setShelters(shelters)
        if (shelters.isEmpty()) {
            textViewShelterMessage.visibility = View.VISIBLE
            recyclerViewShelter.visibility = View.GONE
        } else {
            textViewShelterMessage.visibility = View.GONE
            recyclerViewShelter.visibility = View.VISIBLE
        }
    }

    companion object {

        fun getIntent(context: Context, flags: Int): Intent {
            val intent = Intent(context, ShelterResultActivity::class.java)
            intent.addFlags(flags)
            return intent
        }
    }
}
