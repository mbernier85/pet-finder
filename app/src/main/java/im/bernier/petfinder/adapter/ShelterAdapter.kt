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

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import im.bernier.petfinder.R
import im.bernier.petfinder.model.Shelter
import java.util.*

/**
 * Created by Michael on 2016-10-30.
 */

class ShelterAdapter(private val listener: ShelterAdapter.ShelterItemListener) : androidx.recyclerview.widget.RecyclerView.Adapter<ShelterAdapter.ShelterViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShelterAdapter.ShelterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shelter, parent, false)
        val holder = ShelterViewHolder(view)
        view.setOnClickListener {
            listener.itemClick(shelters[holder.adapterPosition])
        }
        holder.directionsButton.setOnClickListener {
            listener.directionClick(shelters[holder.adapterPosition])
        }

        holder.emailButton.setOnClickListener {
            listener.emailClick(shelters[holder.adapterPosition])
        }

        holder.phoneButton.setOnClickListener {
            listener.phoneClick(shelters[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ShelterAdapter.ShelterViewHolder, position: Int) {
        holder.bindItem(shelters[position])
    }

    override fun getItemCount(): Int {
        return shelters.size
    }

    class ShelterViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.item_shelter_city_text_view)
        lateinit var cityTextView: TextView

        @BindView(R.id.item_shelter_name_text_view)
        lateinit var nameTextView: TextView

        @BindView(R.id.item_shelter_directions_button)
        lateinit var directionsButton: Button

        @BindView(R.id.item_shelter_email_text_view)
        lateinit var emailTextView: TextView

        @BindView(R.id.item_shelter_phone_text_view)
        lateinit var phoneTextView: TextView

        @BindView(R.id.item_shelter_phone_button)
        lateinit var phoneButton: Button

        @BindView(R.id.item_shelter_email_button)
        lateinit var emailButton: Button

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bindItem(shelter: Shelter) {
            val builder = StringBuilder()
            if (!TextUtils.isEmpty(shelter.address1)) {
                builder.append(shelter.address1)
                builder.append(", ")
            }
            builder.append(String.format("%s, %s, %s", shelter.zip, shelter.state, shelter.country))
            cityTextView.text = builder
            nameTextView.text = shelter.name

            if (TextUtils.isEmpty(shelter.email)) {
                emailTextView.visibility = View.GONE
                emailButton.visibility = View.GONE
            } else {
                emailTextView.visibility = View.VISIBLE
                emailButton.visibility = View.VISIBLE
                emailTextView.text = shelter.email
            }

            if (TextUtils.isEmpty(shelter.phone)) {
                phoneButton.visibility = View.GONE
                phoneTextView.visibility = View.GONE
            } else {
                phoneTextView.visibility = View.VISIBLE
                phoneButton.visibility = View.VISIBLE
                phoneTextView.text = shelter.phone
            }
        }
    }
}
