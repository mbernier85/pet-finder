package im.bernier.petfinder.adapter

import android.content.Context
import android.widget.ArrayAdapter
import im.bernier.petfinder.R
import im.bernier.petfinder.model.Animal

class AnimalAdapter(
    context: Context,
    val animals: List<Animal>
) : ArrayAdapter<Animal>(context, R.layout.support_simple_spinner_dropdown_item, animals) {
    override fun getCount(): Int {
        return animals.size
    }
}