package im.bernier.petfinder.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnItemSelected;
import im.bernier.petfinder.R;
import im.bernier.petfinder.model.Animal;
import im.bernier.petfinder.model.Search;
import im.bernier.petfinder.presenter.SearchPresenter;
import im.bernier.petfinder.view.SearchView;

/**
 * Created by Michael on 2016-07-12.
 */

public class SearchActivity extends AppCompatActivity implements SearchView {

    private SearchPresenter presenter;
    private AnimalAdapter animalAdapter;
    private ArrayAdapter<String> breedAdapter;
    private ArrayAdapter<String> ageAdapter;
    private ArrayAdapter<String> sexAdapter;

    String[] ages = {"Any", "baby", "young", "adult", "senior"};
    String[] sexes = {"Any", "M", "F"};
    String[] breed = {"Any"};

    @BindView(R.id.search_toolbar)
    Toolbar toolbar;

    @BindView(R.id.search_animal_spinner)
    Spinner animalSpinner;

    @BindView(R.id.search_location_text_view)
    TextView searchLocation;

    @BindView(R.id.search_breed_auto_complete)
    AutoCompleteTextView breedAutoComplete;

    @BindView(R.id.search_age_spinner)
    Spinner ageSpinner;

    @BindView(R.id.search_sex_spinner)
    Spinner sexSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        presenter = new SearchPresenter();
        presenter.setView(this);
        presenter.onAttach();

        setSupportActionBar(toolbar);
    }

    @Override
    public void setAnimalsSpinner(String[] animals) {
        ArrayList<Animal> animalArrayList = new ArrayList<>(animals.length);
        animalArrayList.add(new Animal(getString(R.string.any), null));
        for (int i = 0; i < animals.length; i++) {
            int id = getResources().getIdentifier(animals[i], "string", getPackageName());
            animalArrayList.add(new Animal(getString(id), animals[i]));
        }
        animalAdapter = new AnimalAdapter(this, animalArrayList);
        animalSpinner.setAdapter(animalAdapter);

        breedAdapter = new ArrayAdapter<>(this, android.support.design.R.layout.support_simple_spinner_dropdown_item , breed);
        breedAutoComplete.setAdapter(breedAdapter);
        breedAutoComplete.setThreshold(1);

        ageAdapter = new ArrayAdapter<>(this, android.support.design.R.layout.support_simple_spinner_dropdown_item, ages);
        ageSpinner.setAdapter(ageAdapter);

        sexAdapter = new ArrayAdapter<>(this, android.support.design.R.layout.support_simple_spinner_dropdown_item, sexes);
        sexSpinner.setAdapter(sexAdapter);
    }

    @OnEditorAction(value = R.id.search_location_text_view)
    public boolean onSearchTextView(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            searchClick();
            return true;
        }
        return false;
    }

    @Override
    public void showError(String message) {
        Snackbar.make(searchLocation, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(@StringRes int stringId) {
        showError(getString(stringId));
    }

    @Override
    public void updateBreeds(ArrayList<String> breeds) {
        breeds.add(0, "Any");
        breedAdapter = new ArrayAdapter<>(this, android.support.design.R.layout.support_simple_spinner_dropdown_item, breeds);
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
                return getString(R.string.any);
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
        search.setLocation(searchLocation.getText().toString().trim());

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
        startActivity(new Intent(this, ResultActivity.class));
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
