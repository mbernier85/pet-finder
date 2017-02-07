package im.bernier.petfinder.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.aboutlibraries.ui.LibsSupportFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.bernier.petfinder.R;
import im.bernier.petfinder.view.PetSearchViewTab;
import im.bernier.petfinder.view.ShelterSearchViewTab;

import static im.bernier.petfinder.R.id.action_about;
import static im.bernier.petfinder.R.id.action_search;
import static im.bernier.petfinder.R.id.action_shelter;

/**
 * Created by Michael on 2016-10-22.
 */

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.content)
    FrameLayout content;

    private SparseArray<ViewGroup> viewGroups;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        viewGroups = new SparseArray<>();
        viewGroups.append(action_search, new PetSearchViewTab(HomeActivity.this));
        content.addView(viewGroups.get(action_search));

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case action_search:
                        content.removeAllViews();
                        if (viewGroups.get(action_search) == null) {
                            viewGroups.append(action_search, new PetSearchViewTab(HomeActivity.this));
                        }
                        content.addView(viewGroups.get(action_search));
                        content.requestLayout();
                        break;
                    case action_shelter:
                        content.removeAllViews();
                        if (viewGroups.get(action_shelter) == null) {
                            viewGroups.append(action_shelter, new ShelterSearchViewTab(HomeActivity.this));
                        }
                        content.addView(viewGroups.get(action_shelter));
                        content.requestLayout();
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

                        break;
                }
                return true;
            }
        });
    }
}
