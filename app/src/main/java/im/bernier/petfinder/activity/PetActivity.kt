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
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.squareup.picasso.Picasso
import im.bernier.petfinder.GlideApp
import im.bernier.petfinder.R
import im.bernier.petfinder.model.Pet
import im.bernier.petfinder.mvp.presenter.PetPresenter
import im.bernier.petfinder.mvp.view.PetView

/**
 * Created by Michael on 2016-07-09.
 */

class PetActivity : BaseActivity(), PetView {

    @BindView(R.id.pet_breed)
    lateinit var breedTextView: TextView

    @BindView(R.id.pet_age_sex)
    lateinit var ageSexTextView: TextView

    @BindView(R.id.pet_description)
    lateinit var descriptionTextView: TextView

    @BindView(R.id.pet_toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.pet_image_view)
    lateinit var petImageView: ImageView

    @BindView(R.id.contact_address)
    lateinit var contactAddressTextView: TextView

    @BindView(R.id.contact_name)
    lateinit var contactNameTextView: TextView

    @BindView(R.id.contact_email)
    lateinit var contactEmailTextView: TextView

    @BindView(R.id.contact_phone)
    lateinit var contactPhoneTextView: TextView

    lateinit var presenter: PetPresenter
    private val size = Point()
    private var height: Float = 0.toFloat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)

        windowManager.defaultDisplay.getSize(size)
        height = resources.getDimension(R.dimen.activity_pet_image_view_height)

        presenter = PetPresenter()
        presenter.setView(this)
        presenter.onAttach()
    }

    override fun updateUi(pet: Pet) {
        if (supportActionBar != null) {
            supportActionBar!!.title = pet.name
        }
        ageSexTextView.text = pet.age + ", " + pet.sex
        breedTextView.text = pet.breed
        descriptionTextView.text = pet.description
        val url = pet.media?.thumbnail
        if (url != null) {
            GlideApp.with(this).load(url).fitCenter().centerCrop().into(petImageView)
//            Picasso.with(this).load(url).resize(size.x, height.toInt()).centerCrop().into(petImageView)
        }

        contactNameTextView.text = pet.contact?.name
        contactAddressTextView.text = pet.contact?.address
        contactEmailTextView.text = pet.contact?.email
        contactPhoneTextView.text = pet.contact?.phone
    }

    override fun doFinish() {
        finish()
    }

    @OnClick(R.id.contact_email)
    internal fun emailClick() {
        presenter.emailClick()
    }

    override fun openMap(pet: Pet) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(String.format("geo:0,0?q=%s", pet.contact?.address)))
        startActivity(intent)
    }

    override fun openDialer(pet: Pet) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse(String.format("tel:%s", pet.contact?.phone)))
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent);
        } else {
            showError(R.string.no_dialer_found)
        }
    }

    fun showError(@StringRes id: Int) {
        Snackbar.make(contactPhoneTextView, id, Snackbar.LENGTH_LONG).show()
    }

    @OnClick(R.id.contact_phone)
    internal fun phoneClick() {
        presenter.phoneClick()
    }

    @OnClick(R.id.contact_address)
    internal fun addressClick() {
        presenter.addressClick()
    }

    override fun openEmail(pet: Pet) {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", pet.contact?.email, null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, String.format("About : %s", pet.name))
        startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }

    @OnClick(R.id.pet_image_view)
    internal fun onImageClick() {
        presenter.onImageClick()
    }

    override fun openImageViewer() {
        startActivity(Intent(this, ImageViewerActivity::class.java))
    }
}
