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
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import im.bernier.petfinder.R;
import im.bernier.petfinder.activity.ResultActivity;
import im.bernier.petfinder.model.Animal;
import im.bernier.petfinder.model.Search;
import im.bernier.petfinder.mvp.presenter.PetSearchPresenter;
import timber.log.Timber;

/**
 * Created by Michael on 2016-07-12.
 */

public class PetSearchViewTab extends FrameLayout implements im.bernier.petfinder.mvp.view.PetSearchView {

    private PetSearchPresenter presenter;
    private AnimalAdapter animalAdapter;
    private ArrayAdapter<String> breedAdapter;
    private ArrayAdapter<String> ageAdapter;
    private ArrayAdapter<String> sexAdapter;

    String[] ages = {"Any", "baby", "young", "adult", "senior"};
    String[] sexes = {"Any", "M", "F"};
    String[] breed = {"Any"};

    @BindView(R.id.search_animal_spinner)
    Spinner animalSpinner;

    @BindView(R.id.search_breed_auto_complete)
    AutoCompleteTextView breedAutoComplete;

    @BindView(R.id.search_age_spinner)
    Spinner ageSpinner;

    @BindView(R.id.search_sex_spinner)
    Spinner sexSpinner;

    private PlaceAutocompleteFragment placeAutocompleteFragment;
    private Geocoder geocoder;
    private String postalCode = "";

    protected void init() {
        inflate(getContext(), R.layout.view_search, this);
        ButterKnife.bind(this);

        geocoder = new Geocoder(getContext());
        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS | AutocompleteFilter.TYPE_FILTER_REGIONS)
                .build();

        placeAutocompleteFragment = (PlaceAutocompleteFragment)((FragmentActivity)getContext()).getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        placeAutocompleteFragment.setFilter(autocompleteFilter);
        placeAutocompleteFragment.setHint(getContext().getString(R.string.location_search));
        placeAutocompleteFragment.getView().findViewById(R.id.place_autocomplete_clear_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postalCode = "";
                ((EditText) placeAutocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input)).setText("");
                view.setVisibility(View.GONE);
            }
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

        presenter = new PetSearchPresenter();
        presenter.setView(this);
        presenter.onAttach();
    }

    public PetSearchViewTab(Context context) {
        super(context);
        init();
    }

    @Override
    public void setAnimalsSpinner(String[] animals) {
        ArrayList<Animal> animalArrayList = new ArrayList<>(animals.length);
        animalArrayList.add(new Animal(getContext().getString(R.string.any), null));
        for (int i = 0; i < animals.length; i++) {
            int id = getResources().getIdentifier(animals[i], "string", getContext().getPackageName());
            animalArrayList.add(new Animal(getContext().getString(id), animals[i]));
        }
        animalAdapter = new AnimalAdapter(getContext(), animalArrayList);
        animalSpinner.setAdapter(animalAdapter);

        breedAdapter = new ArrayAdapter<>(getContext(), android.support.design.R.layout.support_simple_spinner_dropdown_item , breed);
        breedAutoComplete.setAdapter(breedAdapter);
        breedAutoComplete.setThreshold(1);

        ageAdapter = new ArrayAdapter<>(getContext(), android.support.design.R.layout.support_simple_spinner_dropdown_item, ages);
        ageSpinner.setAdapter(ageAdapter);

        sexAdapter = new ArrayAdapter<>(getContext(), android.support.design.R.layout.support_simple_spinner_dropdown_item, sexes);
        sexSpinner.setAdapter(sexAdapter);
    }

    @Override
    public void showError(String message) {
        Snackbar.make(animalSpinner, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(@StringRes int stringId) {
        showError(getContext().getString(stringId));
    }

    @Override
    public void updateBreeds(ArrayList<String> breeds) {
        breeds.add(0, "Any");
        breedAdapter = new ArrayAdapter<>(getContext(), android.support.design.R.layout.support_simple_spinner_dropdown_item, breeds);
        breedAutoComplete.setAdapter(breedAdapter);
        breedAutoComplete.setText("");
        breedAutoComplete.setValidator(new AutoCompleteTextView.Validator() {
            @Override
            public boolean isValid(CharSequence charSequence) {
                for (int i = 0 ; i < breedAdapter.getCount(); i ++) {
                    if (breedAdapter.getItem(i).equals(charSequence.toString())) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public CharSequence fixText(CharSequence charSequence) {
                return getContext().getString(R.string.any);
            }
        });
    }

    @OnClick(R.id.search_breed_auto_complete)
    void breedClick() {
        breedAutoComplete.showDropDown();
    }

    @OnClick(R.id.search_submit)
    void searchClick() {
        Search search = new Search();
        search.setLocation(postalCode);

        int animalPosition = animalSpinner.getSelectedItemPosition();
        if (animalPosition > 0) {
            search.setAnimal(animalAdapter.getItem(animalPosition));
        } else {
            search.setAnimal(null);
        }

        String breedStr = breedAutoComplete.getText().toString().trim();
        if (breedStr.isEmpty() || breedStr.equalsIgnoreCase("any")) {
            search.setBreed(null);
        } else {
            search.setBreed(breedStr);
        }

        int agePosition = ageSpinner.getSelectedItemPosition();
        if (agePosition > 0) {
            search.setAge(ageAdapter.getItem(agePosition));
        } else {
            search.setAge(null);
        }

        int sexPosition = sexSpinner.getSelectedItemPosition();
        if (sexPosition > 0) {
            search.setSex(sexAdapter.getItem(sexPosition));
        } else {
            search.setSex(null);
        }

        presenter.search(search);
    }

    @OnItemSelected(value = R.id.search_animal_spinner, callback = OnItemSelected.Callback.ITEM_SELECTED)
    void onAnimalSelected(int position) {
        String animal = animalAdapter.animals.get(position).getKey();
        presenter.loadBreed(animal);
    }

    @Override
    public void showResults() {
        getContext().startActivity(new Intent(getContext(), ResultActivity.class));
    }

    public class AnimalAdapter extends ArrayAdapter<Animal> {

        private ArrayList<Animal> animals;

        AnimalAdapter(Context context, ArrayList<Animal> animals) {
            super(context, R.layout.support_simple_spinner_dropdown_item, animals);
            this.animals = animals;
        }

        @Override
        public int getCount() {
            return animals.size();
        }
    }
}
