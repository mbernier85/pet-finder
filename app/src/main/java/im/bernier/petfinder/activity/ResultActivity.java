package im.bernier.petfinder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.bernier.petfinder.R;
import im.bernier.petfinder.adapter.PetAdapter;
import im.bernier.petfinder.model.Pet;
import im.bernier.petfinder.mvp.presenter.ResultPresenter;
import im.bernier.petfinder.mvp.view.ResultView;

public class ResultActivity extends AppCompatActivity implements ResultView{

    @BindView(R.id.result_recycler_view)
    RecyclerView recyclerView;

    private ResultPresenter presenter;
    private PetAdapter petAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);
        presenter = new ResultPresenter();
        presenter.setView(this);
        presenter.onAttach();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        petAdapter = new PetAdapter();
        recyclerView.setAdapter(petAdapter);
        petAdapter.setPetClick(new PetAdapter.PetClick() {
            @Override
            public void onClick(Pet pet) {
                presenter.onPetClick(pet);
            }
        });
    }

    @Override
    public void showError(String error) {
        Snackbar.make(recyclerView, error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void openPet(Pet pet) {
        startActivity(new Intent(this, PetActivity.class));
    }

    @Override
    public void updateResults(ArrayList<Pet> pets) {
        petAdapter.setPets(pets);
    }

}
