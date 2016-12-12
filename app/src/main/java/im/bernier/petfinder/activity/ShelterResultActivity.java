package im.bernier.petfinder.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.bernier.petfinder.R;
import im.bernier.petfinder.adapter.ShelterAdapter;
import im.bernier.petfinder.model.Shelter;
import im.bernier.petfinder.mvp.presenter.ShelterResultPresenter;
import im.bernier.petfinder.mvp.view.ShelterResultView;

/**
 * Created by Michael on 2016-10-30.
 */

public class ShelterResultActivity extends AppCompatActivity implements ShelterResultView {

    @BindView(R.id.activity_shelter_recycler_view)
    RecyclerView shelterRecyclerView;

    @BindView(R.id.activity_shelter_progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.activity_shelter_no_results_text_view)
    TextView noResultsTextView;

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

        presenter = new ShelterResultPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onAttach();
    }

    @Override
    protected void onStop() {
        presenter.onDetach();
        super.onStop();
    }

    @Override
    public void showError(String error) {
        Snackbar.make(shelterRecyclerView, error, Snackbar.LENGTH_LONG).show();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        shelterRecyclerView.setVisibility(View.GONE);
        noResultsTextView.setVisibility(View.GONE);
    }

    @Override
    public void showResults(List<Shelter> shelters) {
        progressBar.setVisibility(View.GONE);
        adapter.setShelters(shelters);
        if (shelters.size() == 0) {
            noResultsTextView.setVisibility(View.VISIBLE);
            shelterRecyclerView.setVisibility(View.GONE);
        } else {
            noResultsTextView.setVisibility(View.GONE);
            shelterRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
