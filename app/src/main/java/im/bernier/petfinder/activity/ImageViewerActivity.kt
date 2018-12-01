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
import butterknife.BindView
import butterknife.ButterKnife
import im.bernier.petfinder.R
import im.bernier.petfinder.adapter.ImageViewAdapter
import im.bernier.petfinder.model.Pet
import im.bernier.petfinder.mvp.presenter.ImageViewerPresenter
import im.bernier.petfinder.mvp.view.ImageViewerView
import java.util.*

/**
 * Created by Michael on 2016-07-12.
 */

class ImageViewerActivity : BaseActivity(), ImageViewerView {

    @BindView(R.id.activity_image_view_pager)
    lateinit var viewPager: androidx.viewpager.widget.ViewPager

    lateinit var presenter: ImageViewerPresenter
    private lateinit var imageViewAdapter: ImageViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view)
        ButterKnife.bind(this)

        imageViewAdapter = ImageViewAdapter(this, ArrayList())
        viewPager.adapter = imageViewAdapter

        presenter = ImageViewerPresenter()
        presenter.setView(this)
        presenter.onAttach()
    }

    override fun doFinish() {
        finish()
    }

    override fun updateUi(pet: Pet) {
        pet.media?.hiResPhotos?.let { imageViewAdapter.update(it) }
    }
}
