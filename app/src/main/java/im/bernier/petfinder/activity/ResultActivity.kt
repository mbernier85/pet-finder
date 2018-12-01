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
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import im.bernier.petfinder.R
import im.bernier.petfinder.adapter.PetAdapter
import im.bernier.petfinder.model.Pet
import im.bernier.petfinder.mvp.presenter.PetResultPresenter
import im.bernier.petfinder.mvp.view.ResultView
import java.util.*

class ResultActivity : BaseActivity(), ResultView {

    @BindView(R.id.result_recycler_view)
    lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView

    @BindView(R.id.activity_result_progress_bar)
    lateinit var progressbar: ProgressBar

    @BindView(R.id.activity_result_text_view)
    lateinit var textView: TextView

    lateinit var presenter: PetResultPresenter
    private lateinit var petAdapter: PetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        ButterKnife.bind(this)
        presenter = PetResultPresenter()
        presenter.setView(this)
        presenter.onAttach()

        if (isTablet) {
            recyclerView.layoutManager = GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
        } else {
            recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }
        recyclerView.setHasFixedSize(true)
        petAdapter = PetAdapter()
        recyclerView.adapter = petAdapter
        petAdapter.setPetClick(object: PetAdapter.PetClick {
            override fun onClick(pet: Pet) {
                presenter.onPetClick(pet)
            }
        })
    }

    override fun doFinish() {
        finish()
    }

    override fun showError(error: String) {
        com.google.android.material.snackbar.Snackbar.make(recyclerView, error, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).show()
    }

    override fun openPet(pet: Pet) {
        startActivity(Intent(this, PetActivity::class.java))
    }

    override fun updateResults(pets: ArrayList<Pet>) {
        progressbar.visibility = View.GONE
        if (pets.size > 0) {
            recyclerView.visibility = View.VISIBLE
            textView.visibility = View.GONE
        } else {
            recyclerView.visibility = View.GONE
            textView.visibility = View.VISIBLE
        }
        petAdapter.setPets(pets)
    }

}
