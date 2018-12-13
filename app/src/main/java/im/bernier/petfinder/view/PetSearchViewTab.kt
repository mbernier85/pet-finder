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
import android.view.View
import android.widget.*
import androidx.annotation.StringRes
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment
import com.google.android.material.snackbar.Snackbar
import im.bernier.petfinder.R
import im.bernier.petfinder.activity.PetResultActivity
import im.bernier.petfinder.model.Animal
import im.bernier.petfinder.model.Search
import im.bernier.petfinder.mvp.presenter.PetSearchPresenter
import im.bernier.petfinder.mvp.view.PetSearchView
import kotlinx.android.synthetic.main.view_search.view.*
import timber.log.Timber
import java.io.IOException

/**
 * Created by Michael on 2016-07-12.
 */

class PetSearchViewTab(context: Context) : FrameLayout(context),
    PetSearchView {

    lateinit var presenter: PetSearchPresenter
    private lateinit var animalAdapter: AnimalAdapter
    lateinit var breedAdapter: ArrayAdapter<String>
    private lateinit var ageAdapter: ArrayAdapter<String>
    private lateinit var sexAdapter: ArrayAdapter<String>

    private var ages = arrayOf("Any", "baby", "young", "adult", "senior")
    private var sexes = arrayOf("Any", "M", "F")
    internal var breed = arrayOf("Any")

    private lateinit var placeAutocompleteFragment: SupportPlaceAutocompleteFragment
    lateinit var geocoder: Geocoder
    private var postalCode: String = ""

    private fun init() {
        View.inflate(context, R.layout.view_search, this)

        geocoder = Geocoder(context)
        val autocompleteFilter = AutocompleteFilter.Builder()
            .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS or AutocompleteFilter.TYPE_FILTER_REGIONS)
            .build()

        placeAutocompleteFragment =
                (context as androidx.fragment.app.FragmentActivity).supportFragmentManager.findFragmentById(
                    R.id.fragmentPetSearchAutoComplete
                ) as SupportPlaceAutocompleteFragment
        placeAutocompleteFragment.setFilter(autocompleteFilter)
        placeAutocompleteFragment.setHint(context.getString(R.string.location_search))
        placeAutocompleteFragment.view?.findViewById<View>(R.id.place_autocomplete_clear_button)
            ?.setOnClickListener { view ->
                postalCode = ""
                (placeAutocompleteFragment.view?.findViewById<View>(R.id.place_autocomplete_search_input) as EditText)
                    .setText("")
                view.visibility = View.GONE
            }
        placeAutocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                try {
                    presenter.placeSelected(place)
                    val addresses =
                        geocoder.getFromLocation(place.latLng.latitude, place.latLng.longitude, 5)
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

        autoCompleteTextViewPetSearchBreed.setOnClickListener {
            autoCompleteTextViewPetSearchBreed.showDropDown()
        }
        buttonPetSearchSubmit.setOnClickListener {
            searchClick()
        }
        spinnerPetSearchAnimal.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                onAnimalSelected(position)
            }
        }

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
        spinnerPetSearchAnimal.adapter = animalAdapter

        breedAdapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, breed)
        autoCompleteTextViewPetSearchBreed.setAdapter<ArrayAdapter<String>>(breedAdapter)
        autoCompleteTextViewPetSearchBreed.threshold = 1

        ageAdapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, ages)
        spinnerPetSearchAge.adapter = ageAdapter

        sexAdapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, sexes)
        spinnerPetSearchSex.adapter = sexAdapter
    }

    override fun showError(message: String) {
        Snackbar.make(toolbarPetSearch, message, Snackbar.LENGTH_LONG).show()
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
            postalCode = source.readString()!!
        }

        constructor(superState: Parcelable?) : super(superState)

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeString(postalCode)
        }

        companion object {

            @JvmField
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
        breedAdapter =
                ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, breedList)
        autoCompleteTextViewPetSearchBreed.setAdapter<ArrayAdapter<String>>(breedAdapter)
        autoCompleteTextViewPetSearchBreed.setText("")
        autoCompleteTextViewPetSearchBreed.validator = object : AutoCompleteTextView.Validator {
            override fun isValid(charSequence: CharSequence): Boolean {
                return (0 until breedAdapter.count).any { breedAdapter.getItem(it) == charSequence.toString() }
            }

            override fun fixText(charSequence: CharSequence): CharSequence {
                return context.getString(R.string.any)
            }
        }
    }

    internal fun searchClick() {
        val search = Search()
        search.location = postalCode

        val animalPosition = spinnerPetSearchAnimal.selectedItemPosition
        if (animalPosition > 0) {
            search.animal = animalAdapter.getItem(animalPosition)
        } else {
            search.animal = null
        }

        val breedStr = autoCompleteTextViewPetSearchBreed.text.toString().trim { it <= ' ' }
        if (breedStr.isEmpty() || breedStr.equals("any", ignoreCase = true)) {
            search.breed = null
        } else {
            search.breed = breedStr
        }

        val agePosition = spinnerPetSearchAge.selectedItemPosition
        if (agePosition > 0) {
            search.age = ageAdapter.getItem(agePosition)
        } else {
            search.age = null
        }

        val sexPosition = spinnerPetSearchSex.selectedItemPosition
        if (sexPosition > 0) {
            search.sex = sexAdapter.getItem(sexPosition)
        } else {
            search.sex = null
        }

        presenter.search(search)
    }

    internal fun onAnimalSelected(position: Int) {
        val animal = animalAdapter.animals[position].key
        presenter.loadBreed(animal)
    }

    override fun showResults() {
        context.startActivity(Intent(context, PetResultActivity::class.java))
    }

    inner class AnimalAdapter internal constructor(
        context: Context,
        internal val animals: ArrayList<Animal>
    ) : ArrayAdapter<Animal>(context, R.layout.support_simple_spinner_dropdown_item, animals) {

        override fun getCount(): Int {
            return animals.size
        }
    }
}
