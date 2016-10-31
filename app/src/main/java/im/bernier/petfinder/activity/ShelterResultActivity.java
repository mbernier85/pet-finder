package im.bernier.petfinder.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.bernier.petfinder.R;
import im.bernier.petfinder.adapter.ShelterAdapter;
import im.bernier.petfinder.model.Shelter;
import im.bernier.petfinder.mvp.presenter.ShelterResultPresenter;
import im.bernier.petfinder.mvp.view.IShelterResultView;

/**
 * Created by Michael on 2016-10-30.
 */

public class ShelterResultActivity extends AppCompatActivity implements IShelterResultView {

    @BindView(R.id.activity_shelter_recycler_view)
    RecyclerView shelterRecyclerView;

    private ShelterResultPresenter presenter;
    private ShelterAdapter adapter;

    public static Intent getIntent(Context context, int flags) {
        Intent intent = new Intent(context, ShelterResultActivity.class);
        intent.addFlags(flags);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_result);
        ButterKnife.bind(this);

        shelterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShelterAdapter();
        shelterRecyclerView.setAdapter(adapter);

        presenter = new ShelterResultPresenter();
        presenter.setView(this);
        presenter.onAttach();
    }

    @Override
    public void showError(String error) {
        Snackbar.make(shelterRecyclerView, error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showResults(List<Shelter> shelters) {
        adapter.setShelters(shelters);
    }
}
