package im.bernier.petfinder.activity

import android.support.v7.app.AppCompatActivity

/**
 * Created by Michael on 2017-03-24.
 * Breather Products Inc.
 */

open class BaseActivity : AppCompatActivity() {

    protected val isTablet: Boolean
        get() = resources.configuration.smallestScreenWidthDp >= 600
}
