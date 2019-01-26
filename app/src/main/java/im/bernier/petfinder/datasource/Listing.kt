package im.bernier.petfinder.datasource

import androidx.lifecycle.LiveData

/**
 * Created by Michael on 2019-01-26.
 */
data class Listing<T>(
        val list: LiveData<T>,
        val networkState: LiveData<NetworkState>,
        val retry: () -> Unit
)