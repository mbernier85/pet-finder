package im.bernier.petfinder.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    private AnimalAdapter adapter;

    @BindView(R.id.search_toolbar)
    Toolbar toolbar;

    @BindView(R.id.search_animal_spinner)
    Spinner animalSpinner;

    @BindView(R.id.search_location_text_view)
    TextView searchLocation;

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
        adapter = new AnimalAdapter(this, animalArrayList);
        animalSpinner.setAdapter(adapter);

    }

    @OnClick(R.id.search_submit)
    void searchClick() {
        Search search = new Search();
        search.setAnimal(adapter.getItem(animalSpinner.getSelectedItemPosition()));
        search.setLocation(searchLocation.getText().toString().trim());
        presenter.search(search);
    }

    @Override
    public void showResults() {
        startActivity(new Intent(this, ResultActivity.class));
    }

    public class AnimalAdapter extends ArrayAdapter<Animal> {

        private ArrayList<Animal> animals;

        public AnimalAdapter(Context context, ArrayList<Animal> animals) {
            super(context, R.layout.support_simple_spinner_dropdown_item, animals);
            this.animals = animals;
        }

        @Override
        public int getCount() {
            return animals.size();
        }
    }
}
