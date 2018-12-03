package im.bernier.petfinder.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import im.bernier.petfinder.GlideApp
import im.bernier.petfinder.R
import im.bernier.petfinder.model.Pet
import kotlinx.android.synthetic.main.item_pet.view.*

/**
 * Created by Michael on 2018-12-02.
 */
class PetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var pet: Pet? = null

    fun bind(pet: Pet) {
        this.pet = pet
        itemView.textViewPetItemName.text = String.format("%s, %s, %s", pet.name, pet.age, pet.sex)
        itemView.textViewPetItemBreed.text = pet.breed
        val url = pet.media!!.thumbnail
        if (url != null) {
            GlideApp.with(itemView).load(url).fitCenter().centerCrop().into(itemView.imageViewPetItem)
        } else {
            itemView.imageViewPetItem.setImageResource(R.drawable.ic_broken_image_black_24dp)
        }
    }
}