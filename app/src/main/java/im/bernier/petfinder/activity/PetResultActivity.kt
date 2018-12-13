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

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import im.bernier.petfinder.R
import im.bernier.petfinder.adapter.PetAdapter
import im.bernier.petfinder.model.Pet
import im.bernier.petfinder.mvp.presenter.PetResultPresenter
import im.bernier.petfinder.mvp.view.ResultView
import kotlinx.android.synthetic.main.activity_pet_result.*
import java.util.*

class PetResultActivity : BaseActivity(), ResultView {

    lateinit var presenter: PetResultPresenter
    private lateinit var petAdapter: PetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_result)
        presenter = PetResultPresenter()
        presenter.setView(this)
        presenter.onAttach()

        if (isTablet) {
            recyclerViewPets.layoutManager =
                    GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
        } else {
            recyclerViewPets.layoutManager =
                    LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }
        recyclerViewPets.setHasFixedSize(true)
        petAdapter = PetAdapter()
        recyclerViewPets.adapter = petAdapter
        petAdapter.setPetClick(object : PetAdapter.PetClick {
            override fun onClick(pet: Pet) {
                presenter.onPetClick(pet)
            }
        })
    }

    override fun doFinish() {
        finish()
    }

    override fun showError(error: String) {
        com.google.android.material.snackbar.Snackbar.make(
            recyclerViewPets,
            error,
            com.google.android.material.snackbar.Snackbar.LENGTH_LONG
        ).show()
    }

    override fun openPet(pet: Pet) {
        startActivity(Intent(this, PetActivity::class.java))
    }

    override fun updateResults(pets: ArrayList<Pet>) {
        progressBarPets.visibility = View.GONE
        if (pets.size > 0) {
            recyclerViewPets.visibility = View.VISIBLE
            textViewPets.visibility = View.GONE
        } else {
            recyclerViewPets.visibility = View.GONE
            textViewPets.visibility = View.VISIBLE
        }
        petAdapter.setPets(pets)
    }

}
