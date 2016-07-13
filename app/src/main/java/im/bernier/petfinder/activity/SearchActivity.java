package im.bernier.petfinder.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import im.bernier.petfinder.R;
import im.bernier.petfinder.presenter.SearchPresenter;
import im.bernier.petfinder.view.SearchView;

/**
 * Created by Michael on 2016-07-12.
 */

public class SearchActivity extends AppCompatActivity implements SearchView {

    private SearchPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        presenter = new SearchPresenter();
        presenter.setView(this);
        presenter.onAttach();
    }
}
