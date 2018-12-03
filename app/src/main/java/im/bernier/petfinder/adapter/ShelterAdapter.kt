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
import androidx.recyclerview.widget.RecyclerView
import im.bernier.petfinder.R
import im.bernier.petfinder.holder.ShelterViewHolder
import im.bernier.petfinder.model.Shelter
import kotlinx.android.synthetic.main.item_shelter.view.*
import java.util.*

/**
 * Created by Michael on 2016-10-30.
 */

class ShelterAdapter(private val listener: ShelterAdapter.ShelterItemListener) : RecyclerView.Adapter<ShelterViewHolder>() {

    private var shelters: List<Shelter>

    fun setShelters(shelters: List<Shelter>) {
        this.shelters = shelters
        notifyDataSetChanged()
    }

    interface ShelterItemListener {
        fun itemClick(shelter: Shelter)
        fun phoneClick(shelter: Shelter)
        fun emailClick(shelter: Shelter)
        fun directionClick(shelter: Shelter)
    }

    init {
        shelters = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShelterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shelter, parent, false)
        val holder = ShelterViewHolder(view)
        view.setOnClickListener {
            listener.itemClick(shelters[holder.adapterPosition])
        }
        holder.itemView.buttonShelterDirection.setOnClickListener {
            listener.directionClick(shelters[holder.adapterPosition])
        }

        holder.itemView.buttonShelterEmail.setOnClickListener {
            listener.emailClick(shelters[holder.adapterPosition])
        }

        holder.itemView.buttonShelterPhone.setOnClickListener {
            listener.phoneClick(shelters[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ShelterViewHolder, position: Int) {
        holder.bindItem(shelters[position])
    }

    override fun getItemCount(): Int {
        return shelters.size
    }
}
