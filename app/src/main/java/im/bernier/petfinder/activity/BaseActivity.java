package im.bernier.petfinder.activity;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Michael on 2017-03-24.
 * Breather Products Inc.
 */

public class BaseActivity extends AppCompatActivity {

    protected boolean isTablet() {
        return getResources().getConfiguration().smallestScreenWidthDp >= 600;
    }
}
