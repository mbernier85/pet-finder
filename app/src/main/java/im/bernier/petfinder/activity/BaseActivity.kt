package im.bernier.petfinder.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Michael on 2017-03-24.
 * Breather Products Inc.
 */

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    protected val isTablet: Boolean
        get() = resources.configuration.smallestScreenWidthDp >= 600
}
