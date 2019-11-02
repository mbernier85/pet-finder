package im.bernier.petfinder.fragment

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment
import com.google.android.material.snackbar.Snackbar
import im.bernier.petfinder.R
import im.bernier.petfinder.activity.ShelterResultActivity
import im.bernier.petfinder.datasource.Storage
import im.bernier.petfinder.model.ShelterSearch
import kotlinx.android.synthetic.main.content_shelter_search.*
import timber.log.Timber
import java.io.IOException

class ShelterFragment : BaseFragment() {

    private lateinit var placeAutocompleteFragment: SupportPlaceAutocompleteFragment
    private lateinit var geocoder: Geocoder
    private var postalCode: String = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        geocoder = Geocoder(context)
        val autocompleteFilter = AutocompleteFilter.Builder()
            .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS or AutocompleteFilter.TYPE_FILTER_REGIONS)
            .build()

        placeAutocompleteFragment =
                childFragmentManager.findFragmentById(R.id.fragmentShelterSearchAutoComplete) as SupportPlaceAutocompleteFragment
        placeAutocompleteFragment.setFilter(autocompleteFilter)
        placeAutocompleteFragment.setHint(getString(R.string.location_search))
        val autoCompleteView = placeAutocompleteFragment.view
        val autoCompleteClearButton =
            autoCompleteView?.findViewById<View>(R.id.place_autocomplete_clear_button)
        val autoCompleteEditText = placeAutocompleteFragment.view
            ?.findViewById<EditText>(R.id.place_autocomplete_search_input)
        autoCompleteClearButton?.setOnClickListener { view ->
            postalCode = ""
            autoCompleteEditText?.setText("")
            view.visibility = View.GONE
        }
        placeAutocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                try {
                    val addresses =
                        geocoder.getFromLocation(place.latLng.latitude, place.latLng.longitude, 5)
                    for (address in addresses) {
                        if (address.postalCode != null && address.postalCode.length > 3) {
                            postalCode = address.postalCode
                            return
                        }
                    }
                    if (postalCode.length < 4) {
                        showError(getString(R.string.empty_zip_error))
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
        buttonShelterSearchSubmit.setOnClickListener {
            submitClick()
        }
    }

    private fun submitClick() {
        val name = textInputEditTextShelterSearch.text.toString().trim { it <= ' ' }
        Storage.shelterSearch = ShelterSearch(postalCode, name)
        openShelter()
    }

    private fun openShelter() {
        val intent = ShelterResultActivity.getIntent(context!!, 0)
        context?.startActivity(intent)
    }

    private fun showError(message: String) {
        Snackbar.make(
            textInputEditTextShelterSearch,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shelter_search, container, false)
    }

    companion object {
        fun getInstance(): ShelterFragment {
            return ShelterFragment()
        }
    }
}