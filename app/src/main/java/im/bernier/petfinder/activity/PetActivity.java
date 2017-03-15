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

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.bernier.petfinder.R;
import im.bernier.petfinder.model.Pet;
import im.bernier.petfinder.mvp.presenter.PetPresenter;
import im.bernier.petfinder.mvp.view.PetView;

/**
 * Created by Michael on 2016-07-09.
 */

public class PetActivity extends AppCompatActivity  implements PetView {

    @BindView(R.id.pet_breed)
    TextView breedTextView;

    @BindView(R.id.pet_age_sex)
    TextView ageSexTextView;

    @BindView(R.id.pet_description)
    TextView descriptionTextView;

    @BindView(R.id.pet_toolbar)
    Toolbar toolbar;

    @BindView(R.id.pet_image_view)
    ImageView petImageView;

    @BindView(R.id.contact_address)
    TextView contactAddressTextView;

    @BindView(R.id.contact_name)
    TextView contactNameTextView;

    @BindView(R.id.contact_email)
    TextView contactEmailTextView;

    @BindView(R.id.contact_phone)
    TextView contactPhoneTextView;

    private PetPresenter presenter;
    private Point size = new Point();
    private float height;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        getWindowManager().getDefaultDisplay().getSize(size);
        height = getResources().getDimension(R.dimen.activity_pet_image_view_height);

        presenter = new PetPresenter();
        presenter.setView(this);
        presenter.onAttach();
    }

    @Override
    public void updateUi(Pet pet) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(pet.getName());
        }
        ageSexTextView.setText(pet.getAge() + ", " + pet.getSex());
        breedTextView.setText(pet.getBreed());
        descriptionTextView.setText(pet.getDescription());
        String url = pet.getMedia().getThumbnail();
        if (url != null) {
            Picasso.with(this).load(url).resize(size.x, (int)height).centerCrop().into(petImageView);
        }

        contactNameTextView.setText(pet.getContact().getName());
        contactAddressTextView.setText(pet.getContact().getAddress());
        contactEmailTextView.setText(pet.getContact().getEmail());
        contactPhoneTextView.setText(pet.getContact().getPhone());
    }

    @Override
    public void doFinish() {
        finish();
    }

    @OnClick(R.id.contact_email)
    void emailClick() {
        presenter.emailClick();
    }

    @Override
    public void openMap(Pet pet) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("geo:0,0?q=%s", pet.getContact().getAddress())));
        startActivity(intent);
    }

    @Override
    public void openDialer(Pet pet) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(String.format("tel:%s", pet.getContact().getPhone())));
        startActivity(intent);
    }

    @OnClick(R.id.contact_phone)
    void phoneClick() {
        presenter.phoneClick();
    }

    @OnClick(R.id.contact_address)
    void addressClick() {
        presenter.addressClick();
    }

    @Override
    public void openEmail(Pet pet) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", pet.getContact().getEmail(), null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, String.format("About : %s", pet.getName()));
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    @OnClick(R.id.pet_image_view)
    void onImageClick() {
        presenter.onImageClick();
    }

    @Override
    public void openImageViewer(Pet pet) {
        startActivity(new Intent(this, ImageViewerActivity.class));
    }
}
