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
import android.content.Intent
import android.location.Geocoder
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnItemSelected
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import im.bernier.petfinder.R
import im.bernier.petfinder.activity.ResultActivity
import im.bernier.petfinder.model.Animal
import im.bernier.petfinder.model.Search
import im.bernier.petfinder.mvp.presenter.PetSearchPresenter
import timber.log.Timber
import java.io.IOException

/**
 * Created by Michael on 2016-07-12.
 */

class PetSearchViewTab(context: Context) : FrameLayout(context), im.bernier.petfinder.mvp.view.PetSearchView {

    lateinit var presenter: PetSearchPresenter
    lateinit var animalAdapter: AnimalAdapter
    lateinit var breedAdapter: ArrayAdapter<String>
    lateinit var ageAdapter: ArrayAdapter<String>
    lateinit var sexAdapter: ArrayAdapter<String>

    internal var ages = arrayOf("Any", "baby", "young", "adult", "senior")
    internal var sexes = arrayOf("Any", "M", "F")
    internal var breed = arrayOf("Any")

    @BindView(R.id.search_animal_spinner)
    lateinit var animalSpinner: Spinner

    @BindView(R.id.search_breed_auto_complete)
    lateinit var breedAutoComplete: AutoCompleteTextView

    @BindView(R.id.search_age_spinner)
    lateinit var ageSpinner: Spinner

    @BindView(R.id.search_sex_spinner)
    lateinit var sexSpinner: Spinner

    lateinit var placeAutocompleteFragment: PlaceAutocompleteFragment
    lateinit var geocoder: Geocoder
    private var postalCode: String = ""

    private fun init() {
        View.inflate(context, R.layout.view_search, this)
        ButterKnife.bind(this)

        geocoder = Geocoder(context)
        val autocompleteFilter = AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS or AutocompleteFilter.TYPE_FILTER_REGIONS)
                .build()

        placeAutocompleteFragment = (context as FragmentActivity).fragmentManager.findFragmentById(R.id.place_autocomplete_fragment) as PlaceAutocompleteFragment
        placeAutocompleteFragment.setFilter(autocompleteFilter)
        placeAutocompleteFragment.setHint(context.getString(R.string.location_search))
        placeAutocompleteFragment.view.findViewById<View>(R.id.place_autocomplete_clear_button).setOnClickListener { view ->
            postalCode = ""
            (placeAutocompleteFragment.view.findViewById<View>(R.id.place_autocomplete_search_input) as EditText).setText("")
            view.visibility = View.GONE
        }
        placeAutocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                try {
                    presenter.placeSelected(place)
                    val addresses = geocoder.getFromLocation(place.latLng.latitude, place.latLng.longitude, 5)
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

        id = R.id.search_view
        isSaveEnabled = true

        presenter = PetSearchPresenter()
        presenter.setView(this)
        presenter.onAttach()
    }

    init {
        init()
    }

    override fun setAnimalsSpinner(animals: Array<String>) {
        val animalArrayList = ArrayList<Animal>(animals.size)
        animalArrayList.add(Animal(context.getString(R.string.any), null))
        for (i in animals.indices) {
            val id = resources.getIdentifier(animals[i], "string", context.packageName)
            animalArrayList.add(Animal(context.getString(id), animals[i]))
        }
        animalAdapter = AnimalAdapter(context, animalArrayList)
        animalSpinner.adapter = animalAdapter

        breedAdapter = ArrayAdapter(context, android.support.design.R.layout.support_simple_spinner_dropdown_item, breed)
        breedAutoComplete.setAdapter<ArrayAdapter<String>>(breedAdapter)
        breedAutoComplete.threshold = 1

        ageAdapter = ArrayAdapter(context, android.support.design.R.layout.support_simple_spinner_dropdown_item, ages)
        ageSpinner.adapter = ageAdapter

        sexAdapter = ArrayAdapter(context, android.support.design.R.layout.support_simple_spinner_dropdown_item, sexes)
        sexSpinner.adapter = sexAdapter
    }

    override fun showError(message: String) {
        Snackbar.make(animalSpinner, message, Snackbar.LENGTH_LONG).show()
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

        val viewState = state
        super.onRestoreInstanceState(viewState.superState)

        this.postalCode = viewState.postalCode
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

    override fun updateBreeds(breeds: List<String>) {
        val breedList: ArrayList<String> = ArrayList(breeds)
        breedList.add(0, "Any")
        breedAdapter = ArrayAdapter(context, android.support.design.R.layout.support_simple_spinner_dropdown_item, breedList)
        breedAutoComplete.setAdapter<ArrayAdapter<String>>(breedAdapter)
        breedAutoComplete.setText("")
        breedAutoComplete.validator = object : AutoCompleteTextView.Validator {
            override fun isValid(charSequence: CharSequence): Boolean {
                for (i in 0..breedAdapter.count - 1) {
                    if (breedAdapter.getItem(i) == charSequence.toString()) {
                        return true
                    }
                }
                return false
            }

            override fun fixText(charSequence: CharSequence): CharSequence {
                return context.getString(R.string.any)
            }
        }
    }

    @OnClick(R.id.search_breed_auto_complete)
    internal fun breedClick() {
        breedAutoComplete.showDropDown()
    }

    @OnClick(R.id.search_submit)
    internal fun searchClick() {
        val search = Search()
        search.location = postalCode

        val animalPosition = animalSpinner.selectedItemPosition
        if (animalPosition > 0) {
            search.animal = animalAdapter.getItem(animalPosition)
        } else {
            search.animal = null
        }

        val breedStr = breedAutoComplete.text.toString().trim { it <= ' ' }
        if (breedStr.isEmpty() || breedStr.equals("any", ignoreCase = true)) {
            search.breed = null
        } else {
            search.breed = breedStr
        }

        val agePosition = ageSpinner.selectedItemPosition
        if (agePosition > 0) {
            search.age = ageAdapter.getItem(agePosition)
        } else {
            search.age = null
        }

        val sexPosition = sexSpinner.selectedItemPosition
        if (sexPosition > 0) {
            search.sex = sexAdapter.getItem(sexPosition)
        } else {
            search.sex = null
        }

        presenter.search(search)
    }

    @OnItemSelected(value = R.id.search_animal_spinner, callback = OnItemSelected.Callback.ITEM_SELECTED)
    internal fun onAnimalSelected(position: Int) {
        val animal = animalAdapter.animals[position].key
        presenter.loadBreed(animal)
    }

    override fun showResults() {
        context.startActivity(Intent(context, ResultActivity::class.java))
    }

    inner class AnimalAdapter internal constructor(context: Context, internal val animals: ArrayList<Animal>) : ArrayAdapter<Animal>(context, R.layout.support_simple_spinner_dropdown_item, animals) {

        override fun getCount(): Int {
            return animals.size
        }
    }
}
