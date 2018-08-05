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

package im.bernier.petfinder.view

import android.content.Context
import android.location.Geocoder
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.FragmentActivity
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import im.bernier.petfinder.R
import im.bernier.petfinder.activity.ShelterResultActivity
import im.bernier.petfinder.mvp.presenter.ShelterSearchPresenter
import im.bernier.petfinder.mvp.view.ShelterSearchView
import timber.log.Timber
import java.io.IOException

/**
 * Created by Michael on 2016-10-29.
 */

class ShelterSearchViewTab(context: Context) : FrameLayout(context), ShelterSearchView {

    @BindView(R.id.search_shelter_name_edit_text)
    lateinit var nameEditText: EditText

    private lateinit var placeAutocompleteFragment: PlaceAutocompleteFragment
    lateinit var presenter: ShelterSearchPresenter
    private var geocoder: Geocoder? = null
    private var postalCode: String = ""

    init {
        init()
    }

    fun init() {
        View.inflate(context, R.layout.view_shelter_search, this)
        ButterKnife.bind(this)

        geocoder = Geocoder(context)
        val autocompleteFilter = AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS or AutocompleteFilter.TYPE_FILTER_REGIONS)
                .build()

        placeAutocompleteFragment = (context as androidx.fragment.app.FragmentActivity).fragmentManager.findFragmentById(R.id.shelter_place_autocomplete_fragment) as PlaceAutocompleteFragment
        placeAutocompleteFragment.setFilter(autocompleteFilter)
        placeAutocompleteFragment.setHint(context.getString(R.string.location_search))
        placeAutocompleteFragment.view!!.findViewById<View>(R.id.place_autocomplete_clear_button).setOnClickListener { view ->
            postalCode = ""
            (placeAutocompleteFragment.view!!.findViewById<View>(R.id.place_autocomplete_search_input) as EditText).setText("")
            view.visibility = View.GONE
        }
        placeAutocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                try {
                    presenter.placeSelected(place)
                    val addresses = geocoder!!.getFromLocation(place.latLng.latitude, place.latLng.longitude, 5)
                    for (address in addresses) {
                        if (address.postalCode != null && address.postalCode.length > 3) {
                            postalCode = address.postalCode
                            return
                        }
                    }
                    if (postalCode.length < 4) {
                        showError(context.getString(R.string.empty_zip_error))
                    }
                } catch (e: IOException) {
                    showError(e.message.orEmpty())
                    Timber.e(e)
                }

            }

            override fun onError(status: Status) {
                Timber.e(status.statusMessage)
            }
        })

        id = R.id.shelter_search_view
        isSaveEnabled = true

        presenter = ShelterSearchPresenter(this)
    }


    override fun onSaveInstanceState(): Parcelable? {
        val parcelable = super.onSaveInstanceState()
        val viewState = ViewState(parcelable)
        viewState.postalCode = this.postalCode
        return viewState
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        //begin boilerplate code so parent classes can restore state
        if (state !is ViewState) {
            super.onRestoreInstanceState(state)
            return
        }

        super.onRestoreInstanceState(state.superState)

        this.postalCode = state.postalCode
    }

    internal class ViewState : View.BaseSavedState {
        internal var postalCode: String = ""

        constructor(source: Parcel) : super(source) {
            postalCode = source.readString()
        }

        constructor(superState: Parcelable) : super(superState) {}

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeString(postalCode)
        }

        companion object {

            val CREATOR: Parcelable.Creator<ViewState> = object : Parcelable.Creator<ViewState> {
                override fun createFromParcel(`in`: Parcel): ViewState {
                    return ViewState(`in`)
                }

                override fun newArray(size: Int): Array<ViewState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

    override fun showError(@StringRes id: Int) {
        showError(context.getString(id))
    }

    private fun showError(message: String) {
        com.google.android.material.snackbar.Snackbar.make(nameEditText, message, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).show()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.onAttach()
    }

    override fun onDetachedFromWindow() {
        presenter.onDetach()
        super.onDetachedFromWindow()
    }

    @OnClick(R.id.search_shelter_button_submit)
    internal fun submitClick() {
        val name = nameEditText.text.toString().trim { it <= ' ' }
        presenter.submit(postalCode, name)
    }

    override fun openShelter() {
        val intent = ShelterResultActivity.getIntent(this.context, 0)
        context.startActivity(intent)
    }
}
