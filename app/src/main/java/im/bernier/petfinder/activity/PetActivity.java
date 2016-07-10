package im.bernier.petfinder.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import im.bernier.petfinder.R;
import im.bernier.petfinder.datasource.Storage;
import im.bernier.petfinder.presenter.PetPresenter;
import im.bernier.petfinder.view.PetView;

/**
 * Created by Michael on 2016-07-09.
 */

public class PetActivity extends AppCompatActivity  implements PetView {

    private PetPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);

        presenter = new PetPresenter();
        presenter.setView(this);
        presenter.onAttach();

    }
}
