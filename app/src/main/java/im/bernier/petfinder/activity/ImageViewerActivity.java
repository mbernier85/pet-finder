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

package im.bernier.petfinder.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.bernier.petfinder.R;
import im.bernier.petfinder.adapter.ImageViewAdapter;
import im.bernier.petfinder.model.Pet;
import im.bernier.petfinder.model.Photo;
import im.bernier.petfinder.mvp.presenter.ImageViewerPresenter;
import im.bernier.petfinder.mvp.view.ImageViewerView;

/**
 * Created by Michael on 2016-07-12.
 */

public class ImageViewerActivity extends AppCompatActivity implements ImageViewerView {

    @BindView(R.id.activity_image_view_pager)
    ViewPager viewPager;

    private ImageViewerPresenter presenter;
    private ImageViewAdapter imageViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        ButterKnife.bind(this);

        imageViewAdapter = new ImageViewAdapter(this, new ArrayList<Photo>());
        viewPager.setAdapter(imageViewAdapter);

        presenter = new ImageViewerPresenter();
        presenter.setView(this);
        presenter.onAttach();
    }

    @Override
    public void updateUi(Pet pet) {
        imageViewAdapter.update(pet.getMedia().getHiResPhotos());
    }
}
