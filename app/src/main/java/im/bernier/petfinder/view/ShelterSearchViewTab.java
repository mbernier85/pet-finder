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

package im.bernier.petfinder.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.bernier.petfinder.R;
import im.bernier.petfinder.activity.ShelterResultActivity;
import im.bernier.petfinder.mvp.presenter.ShelterSearchPresenter;
import im.bernier.petfinder.mvp.view.ShelterSearchView;

/**
 * Created by Michael on 2016-10-29.
 */

public class ShelterSearchViewTab extends FrameLayout implements ShelterSearchView {

    @BindView(R.id.search_shelter_location_edit_text)
    EditText locationEditText;

    @BindView(R.id.search_shelter_name_edit_text)
    EditText nameEditText;

    @BindView(R.id.search_shelter_button_submit)
    Button submitButton;

    private ShelterSearchPresenter presenter;

    public ShelterSearchViewTab(Context context) {
        super(context);
        init();
    }

    public ShelterSearchViewTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShelterSearchViewTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        inflate(getContext(), R.layout.view_shelter_search, this);
        ButterKnife.bind(this);

        presenter = new ShelterSearchPresenter(this);
    }

    @Override
    public void showLocationEmpty() {
        locationEditText.setError(getContext().getString(R.string.empty_zip_error));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.onAttach();
    }

    @Override
    protected void onDetachedFromWindow() {
        presenter.onDetach();
        super.onDetachedFromWindow();
    }

    @OnClick(R.id.search_shelter_button_submit)
    void submitClick() {
        String location = locationEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        presenter.submit(location, name);
    }

    @Override
    public void openShelter() {
        Intent intent = ShelterResultActivity.getIntent(this.getContext(), 0);
        getContext().startActivity(intent);
    }
}
