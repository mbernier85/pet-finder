package im.bernier.petfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.bernier.petfinder.adapter.PetAdapter;
import im.bernier.petfinder.model.Pet;
import im.bernier.petfinder.presenter.ResultPresenter;
import im.bernier.petfinder.view.ResultView;

public class ResultActivity extends AppCompatActivity implements ResultView{

    @BindView(R.id.result_recycler_view)
    protected RecyclerView recyclerView;

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
    }

    @Override
    public void updateResults(ArrayList<Pet> pets) {
        petAdapter.setPets(pets);
    }

}
