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
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import androidx.annotation.StringRes
import im.bernier.petfinder.GlideApp
import im.bernier.petfinder.R
import im.bernier.petfinder.model.Pet
import im.bernier.petfinder.mvp.presenter.PetPresenter
import im.bernier.petfinder.mvp.view.PetView
import kotlinx.android.synthetic.main.activity_pet.*
import kotlinx.android.synthetic.main.view_address.*

/**
 * Created by Michael on 2016-07-09.
 */

class PetActivity : BaseActivity(), PetView {

    lateinit var presenter: PetPresenter
    private val size = Point()
    private var height: Float = 0.toFloat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet)
        setSupportActionBar(toolbarPet)

        windowManager.defaultDisplay.getSize(size)
        height = resources.getDimension(R.dimen.activity_pet_image_view_height)

        presenter = PetPresenter()
        presenter.setView(this)
        presenter.onAttach()

        textViewContactEmail.setOnClickListener {
            presenter.emailClick()
        }
        textViewContactPhone.setOnClickListener {
            presenter.phoneClick()
        }
        textViewContactAddress.setOnClickListener {
            presenter.addressClick()
        }
        imageViewPet.setOnClickListener {
            presenter.onImageClick()
        }
    }

    override fun updateUi(pet: Pet) {
        if (supportActionBar != null) {
            supportActionBar!!.title = pet.name
        }
        textViewPetAgeSex.text = "${pet.age}, ${pet.sex}"
        textViewPetBreed.text = pet.breed
        textViewPetDescription.text = pet.description
        val url = pet.media?.thumbnail
        if (url != null) {
            GlideApp.with(this).load(url).fitCenter().centerCrop().into(imageViewPet)
        }

        textViewContactName.text = pet.contact?.name
        textViewContactAddress.text = pet.contact?.address
        textViewContactEmail.text = pet.contact?.email
        textViewContactPhone.text = pet.contact?.phone
    }

    override fun doFinish() {
        finish()
    }

    override fun openMap(pet: Pet) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(String.format("geo:0,0?q=%s", pet.contact?.address)))
        startActivity(intent)
    }

    override fun openDialer(pet: Pet) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse(String.format("tel:%s", pet.contact?.phone)))
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            showError(R.string.no_dialer_found)
        }
    }

    fun showError(@StringRes id: Int) {
        com.google.android.material.snackbar.Snackbar.make(textViewContactPhone, id, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).show()
    }

    override fun openEmail(pet: Pet) {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", pet.contact?.email, null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, String.format("About : %s", pet.name))
        startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }

    override fun openImageViewer() {
        startActivity(Intent(this, GalleryActivity::class.java))
    }
}
