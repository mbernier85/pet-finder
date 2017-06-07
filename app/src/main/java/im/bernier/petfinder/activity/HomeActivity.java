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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.aboutlibraries.ui.LibsSupportFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.bernier.petfinder.Analytics;
import im.bernier.petfinder.R;
import im.bernier.petfinder.view.PetSearchViewTab;
import im.bernier.petfinder.view.ShelterSearchViewTab;

import static im.bernier.petfinder.R.id.action_about;
import static im.bernier.petfinder.R.id.action_search;
import static im.bernier.petfinder.R.id.action_shelter;

/**
 * Created by Michael on 2016-10-22.
 */

public class HomeActivity extends BaseActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.content)
    FrameLayout content;

    private SparseArray<ViewGroup> viewGroups;
    private Analytics analytics = Analytics.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        viewGroups = new SparseArray<>();
        viewGroups.append(action_search, new PetSearchViewTab(HomeActivity.this));
        content.addView(viewGroups.get(action_search));

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case action_search:
                    content.removeAllViews();
                    if (viewGroups.get(action_search) == null) {
                        viewGroups.append(action_search, new PetSearchViewTab(HomeActivity.this));
                    }
                    content.addView(viewGroups.get(action_search));
                    content.requestLayout();

                    analytics.track("home_pet_search_click");
                    break;
                case action_shelter:
                    content.removeAllViews();
                    if (viewGroups.get(action_shelter) == null) {
                        viewGroups.append(action_shelter, new ShelterSearchViewTab(HomeActivity.this));
                    }
                    content.addView(viewGroups.get(action_shelter));
                    content.requestLayout();
                    analytics.track("home_shelter_search_click");
                    break;
                case action_about:
                    content.removeAllViews();
                    LibsSupportFragment fragment = new LibsBuilder()
                            .withAboutIconShown(true)
                            .withAboutVersionShown(true)
                            .withAboutDescription(getString(R.string.app_description))
                            .supportFragment();

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.content, fragment).commit();
                    analytics.track("home_about_click");
                    break;
            }
            return true;
        });
    }
}
