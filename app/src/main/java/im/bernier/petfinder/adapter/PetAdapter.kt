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

import android.view.LayoutInflater
import android.view.ViewGroup
import im.bernier.petfinder.R
import im.bernier.petfinder.holder.PetViewHolder
import im.bernier.petfinder.model.Pet
import java.util.*

/**
 * Created by Michael on 2016-07-09.
 */

class PetAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<PetViewHolder>() {

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
        holder.itemView.setOnClickListener {
            petClick?.onClick(holder.pet!!)
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


}
