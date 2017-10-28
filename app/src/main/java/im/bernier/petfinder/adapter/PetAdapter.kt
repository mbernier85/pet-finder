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

package im.bernier.petfinder.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.squareup.picasso.Picasso

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import im.bernier.petfinder.GlideApp
import im.bernier.petfinder.R
import im.bernier.petfinder.model.Pet

/**
 * Created by Michael on 2016-07-09.
 */

class PetAdapter : RecyclerView.Adapter<PetAdapter.PetViewHolder>() {

    private var pets = ArrayList<Pet>()
    private var petClick: PetClick? = null

    interface PetClick {
        fun onClick(pet: Pet)
    }

    fun setPetClick(petClick: PetClick) {
        this.petClick = petClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val holder = PetViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pet, parent, false))
        holder.cardView.setOnClickListener { _ ->
            if (petClick != null) {
                holder.pet?.let { petClick?.onClick(it) }
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        holder.bind(pets[position])
    }

    fun setPets(pets: ArrayList<Pet>) {
        this.pets = pets
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return pets.size
    }

    class PetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.item_pet_name)
        lateinit var petName: TextView

        @BindView(R.id.item_pet_picture)
        lateinit var imageView: ImageView

        @BindView(R.id.item_pet_breed)
        lateinit var breed: TextView

        @BindView(R.id.item_pet_card_view)
        lateinit var cardView: CardView

        var pet: Pet? = null

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(pet: Pet) {
            this.pet = pet
            petName.text = String.format("%s, %s, %s", pet.name, pet.age, pet.sex)
            breed.text = pet.breed
            val url = pet.media!!.thumbnail
            if (url != null) {
                GlideApp.with(itemView).load(url).fitCenter().centerCrop().into(imageView)
//                Picasso.with(itemView.context).load(url).fit().centerCrop().into(imageView)
            } else {
                imageView.setImageResource(R.drawable.ic_broken_image_black_24dp)
            }
        }
    }
}
