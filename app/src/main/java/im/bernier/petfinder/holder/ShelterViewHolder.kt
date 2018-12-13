package im.bernier.petfinder.holder

import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import im.bernier.petfinder.model.Shelter
import kotlinx.android.synthetic.main.item_shelter.view.*

/**
 * Created by Michael on 2018-12-02.
 */
class ShelterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindItem(shelter: Shelter) {
        val builder = StringBuilder()
        if (!TextUtils.isEmpty(shelter.address1)) {
            builder.append(shelter.address1)
            builder.append(", ")
        }
        builder.append(String.format("%s, %s, %s", shelter.zip, shelter.state, shelter.country))
        itemView.textViewShelterCity.text = builder
        itemView.textViewShelterName.text = shelter.name

        if (TextUtils.isEmpty(shelter.email)) {
            itemView.textViewShelterEmail.visibility = View.GONE
            itemView.buttonShelterEmail.visibility = View.GONE
        } else {
            itemView.textViewShelterEmail.visibility = View.VISIBLE
            itemView.buttonShelterEmail.visibility = View.VISIBLE
            itemView.textViewShelterEmail.text = shelter.email
        }

        if (TextUtils.isEmpty(shelter.phone)) {
            itemView.buttonShelterPhone.visibility = View.GONE
            itemView.textViewShelterPhone.visibility = View.GONE
        } else {
            itemView.textViewShelterPhone.visibility = View.VISIBLE
            itemView.buttonShelterPhone.visibility = View.VISIBLE
            itemView.textViewShelterPhone.text = shelter.phone
        }
    }
}