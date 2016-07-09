package im.bernier.petfinder.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    @Override
    public PetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PetViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pet, parent, false));
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
        protected TextView petName;

        public PetViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Pet pet) {
            petName.setText(pet.getName());
        }
    }
}
