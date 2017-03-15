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

package im.bernier.petfinder.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.bernier.petfinder.R;
import im.bernier.petfinder.model.Pet;

/**
 * Created by Michael on 2016-07-09.
 */

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    private ArrayList<Pet> pets = new ArrayList<>();
    private PetClick petClick;

    public interface PetClick {
        void onClick(Pet pet);
    }

    public void setPetClick(PetClick petClick) {
        this.petClick = petClick;
    }

    @Override
    public PetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final PetViewHolder holder = new PetViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pet, parent, false));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (petClick != null) {
                    petClick.onClick(holder.pet);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(PetViewHolder holder, int position) {
        holder.bind(pets.get(position));
    }

    public void setPets(ArrayList<Pet> pets) {
        this.pets = pets;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    static class PetViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_pet_name)
        TextView petName;

        @BindView(R.id.item_pet_picture)
        ImageView imageView;

        @BindView(R.id.item_pet_breed)
        TextView breed;

        @BindView(R.id.item_pet_card_view)
        CardView cardView;

        private Pet pet;

        PetViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Pet pet) {
            this.pet = pet;
            petName.setText(pet.getName() + ", " + pet.getAge() + ", " + pet.getSex());
            breed.setText(pet.getBreed());
            String url = pet.getMedia().getThumbnail();
            if (url != null) {
                Picasso.with(itemView.getContext()).load(url).fit().centerCrop().into(imageView);
            } else {
                imageView.setImageResource(R.drawable.ic_broken_image_black_24dp);
            }
        }
    }
}
