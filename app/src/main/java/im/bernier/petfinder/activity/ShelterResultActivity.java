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

package im.bernier.petfinder.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
    public void doFinish() {
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_result);
        ButterKnife.bind(this);

        shelterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShelterAdapter(new ShelterAdapter.ShelterItemListener() {
            @Override
            public void itemClick(Shelter shelter) {

            }

            @Override
            public void phoneClick(Shelter shelter) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(String.format("tel:%s", shelter.getPhone())));
                startActivity(intent);
            }

            @Override
            public void emailClick(Shelter shelter) {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{shelter.getEmail()});

                startActivity(Intent.createChooser(emailIntent, getString(R.string.send_mail)));
            }

            @Override
            public void directionClick(Shelter shelter) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(String.format("http://maps.google.com/maps?daddr=%s, %s", shelter.getCity(), shelter.getZip())));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });
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
