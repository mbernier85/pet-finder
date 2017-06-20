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
import android.location.Address;
import android.location.Geocoder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.bernier.petfinder.R;
import im.bernier.petfinder.activity.ShelterResultActivity;
import im.bernier.petfinder.mvp.presenter.ShelterSearchPresenter;
import im.bernier.petfinder.mvp.view.ShelterSearchView;
import timber.log.Timber;

/**
 * Created by Michael on 2016-10-29.
 */

public class ShelterSearchViewTab extends FrameLayout implements ShelterSearchView {

    @BindView(R.id.search_shelter_name_edit_text)
    EditText nameEditText;

    private PlaceAutocompleteFragment placeAutocompleteFragment;
    private ShelterSearchPresenter presenter;
    private Geocoder geocoder;
    private String postalCode = "";

    public ShelterSearchViewTab(Context context) {
        super(context);
        init();
    }

    public void init() {
        inflate(getContext(), R.layout.view_shelter_search, this);
        ButterKnife.bind(this);

        geocoder = new Geocoder(getContext());
        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS | AutocompleteFilter.TYPE_FILTER_REGIONS)
                .build();

        placeAutocompleteFragment = (PlaceAutocompleteFragment)((FragmentActivity)getContext()).getFragmentManager().findFragmentById(R.id.shelter_place_autocomplete_fragment);
        placeAutocompleteFragment.setFilter(autocompleteFilter);
        placeAutocompleteFragment.setHint(getContext().getString(R.string.location_search));
        placeAutocompleteFragment.getView().findViewById(R.id.place_autocomplete_clear_button).setOnClickListener(view -> {
            postalCode = "";
            ((EditText) placeAutocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input)).setText("");
            view.setVisibility(View.GONE);
        });
        placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                try {
                    presenter.placeSelected(place);
                    List<Address> addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 5);
                    for (Address address: addresses) {
                        if (address.getPostalCode() != null && address.getPostalCode().length() > 3) {
                            postalCode = address.getPostalCode();
                            return;
                        }
                    }
                    if (postalCode == null || postalCode.length() < 4) {
                        showError(getContext().getString(R.string.empty_zip_error));
                    }
                } catch (IOException e) {
                    showError(e.getMessage());
                    Timber.e(e);
                }
            }

            @Override
            public void onError(Status status) {
                Timber.e(status.getStatusMessage());
            }
        });

        setId(R.id.shelter_search_view);
        setSaveEnabled(true);

        presenter = new ShelterSearchPresenter(this);
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        ViewState viewState = new ViewState(parcelable);
        viewState.postalCode = this.postalCode;
        return viewState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        //begin boilerplate code so parent classes can restore state
        if(!(state instanceof ViewState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        ViewState viewState = (ViewState)state;
        super.onRestoreInstanceState(viewState.getSuperState());

        this.postalCode = viewState.postalCode;
    }

    static class ViewState extends BaseSavedState {
        private String postalCode;

        ViewState(Parcel source) {
            super(source);
            postalCode = source.readString();
        }

        ViewState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(postalCode);
        }

        public static final Parcelable.Creator<ViewState> CREATOR =
                new Parcelable.Creator<ViewState>() {
                    public ViewState createFromParcel(Parcel in) {
                        return new ViewState(in);
                    }
                    public ViewState[] newArray(int size) {
                        return new ViewState[size];
                    }
                };
    }

    @Override
    public void showError(@StringRes int stringId) {
        showError(getContext().getString(stringId));
    }

    private void showError(String message) {
        Snackbar.make(nameEditText, message, Snackbar.LENGTH_LONG).show();
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
        String name = nameEditText.getText().toString().trim();
        presenter.submit(postalCode, name);
    }

    @Override
    public void openShelter() {
        Intent intent = ShelterResultActivity.Companion.getIntent(this.getContext(), 0);
        getContext().startActivity(intent);
    }
}
