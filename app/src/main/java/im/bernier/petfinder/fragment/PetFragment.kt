package im.bernier.petfinder.fragment

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.annotation.StringRes
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment
import com.google.android.material.snackbar.Snackbar
import im.bernier.petfinder.R
import im.bernier.petfinder.activity.PetResultActivity
import im.bernier.petfinder.adapter.AnimalAdapter
import im.bernier.petfinder.datasource.Repository
import im.bernier.petfinder.datasource.Storage
import im.bernier.petfinder.model.Animal
import im.bernier.petfinder.model.Breeds
import im.bernier.petfinder.model.Search
import kotlinx.android.synthetic.main.content_pet_search.*
import kotlinx.android.synthetic.main.fragment_pet_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class PetFragment : BaseFragment() {

    private val repository = Repository

    private lateinit var animalAdapter: AnimalAdapter
    lateinit var breedAdapter: ArrayAdapter<String>
    private lateinit var ageAdapter: ArrayAdapter<String>
    private lateinit var sexAdapter: ArrayAdapter<String>

    private var ages = arrayOf("Any", "baby", "young", "adult", "senior")
    private var sexes = arrayOf("Any", "M", "F")
    internal var breed = arrayOf("Any")
    private val animals =
        arrayOf("cat", "dog", "rabbit", "smallfurry", "horse", "bird", "reptile", "pig", "barnyard")


    private lateinit var placeAutocompleteFragment: SupportPlaceAutocompleteFragment
    lateinit var geocoder: Geocoder
    private var postalCode: String = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        geocoder = Geocoder(context)
        val autocompleteFilter = AutocompleteFilter.Builder()
            .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS or AutocompleteFilter.TYPE_FILTER_REGIONS)
            .build()

        placeAutocompleteFragment =
                childFragmentManager.findFragmentById(R.id.fragmentPetSearchAutoComplete) as SupportPlaceAutocompleteFragment

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
        autoCompleteTextViewPetSearchBreed.setOnClickListener {
            autoCompleteTextViewPetSearchBreed.showDropDown()
        }
        buttonPetSearchSubmit.setOnClickListener {
            searchClick()
        }
        spinnerPetSearchAnimal.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
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
        setAnimalsSpinner(animals)
    }

    fun showError(message: String) {
        Snackbar.make(toolbarPetSearch, message, Snackbar.LENGTH_LONG).show()
    }

    fun showError(@StringRes id: Int) {
        showError(getString(id))
    }

    fun onAnimalSelected(position: Int) {
        val animal = animalAdapter.animals[position].key ?: ""
        repository.loadBreeds(animal).enqueue(object : Callback<Breeds?> {
            override fun onFailure(call: Call<Breeds?>, t: Throwable) {
                showError(t.localizedMessage)
            }

            override fun onResponse(call: Call<Breeds?>, response: Response<Breeds?>) {
                val breeds = response.body()
                if (breeds != null) {
                    updateBreeds(breeds.breeds)
                } else {

                }
            }
        })
    }

    private fun showResults() {
        startActivity(Intent(context, PetResultActivity::class.java))
    }

    private fun setAnimalsSpinner(animals: Array<String>) {
        val animalArrayList = mutableListOf<Animal>()
        animalArrayList.add(Animal(context!!.getString(R.string.any), null))
        for (i in animals.indices) {
            val id = resources.getIdentifier(animals[i], "string", context!!.packageName)
            animalArrayList.add(Animal(context!!.getString(id), animals[i]))
        }
        animalAdapter = AnimalAdapter(context!!, animalArrayList)
        spinnerPetSearchAnimal.adapter = animalAdapter

        breedAdapter = ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, breed)
        autoCompleteTextViewPetSearchBreed.setAdapter<ArrayAdapter<String>>(breedAdapter)
        autoCompleteTextViewPetSearchBreed.threshold = 1

        ageAdapter = ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, ages)
        spinnerPetSearchAge.adapter = ageAdapter

        sexAdapter = ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, sexes)
        spinnerPetSearchSex.adapter = sexAdapter
    }

    fun updateBreeds(breeds: List<String>) {
        val breedList: MutableList<String> = breeds.toMutableList()
        breedList.add(0, "Any")
        breedAdapter =
                ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, breedList)
        autoCompleteTextViewPetSearchBreed.setAdapter<ArrayAdapter<String>>(breedAdapter)
        autoCompleteTextViewPetSearchBreed.setText("")
        autoCompleteTextViewPetSearchBreed.validator = object : AutoCompleteTextView.Validator {
            override fun isValid(charSequence: CharSequence): Boolean {
                return (0 until breedAdapter.count).any { breedAdapter.getItem(it) == charSequence.toString() }
            }

            override fun fixText(charSequence: CharSequence): CharSequence {
                return getString(R.string.any)
            }
        }
    }

    private fun searchClick() {
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

        search(search)
    }

    private fun search(search: Search) {
        Storage.search = search
        showResults()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pet_search, container, false)
    }

    companion object {
        fun getInstance(): PetFragment {
            return PetFragment()
        }
    }
}