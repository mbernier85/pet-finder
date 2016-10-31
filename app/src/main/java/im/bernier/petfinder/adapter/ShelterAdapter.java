package im.bernier.petfinder.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.bernier.petfinder.R;
import im.bernier.petfinder.model.Shelter;

/**
 * Created by Michael on 2016-10-30.
 */

public class ShelterAdapter extends RecyclerView.Adapter<ShelterAdapter.ShelterViewHolder> {

    private List<Shelter> shelters;

    public void setShelters(List<Shelter> shelters) {
        this.shelters = shelters;
        notifyDataSetChanged();
    }

    public ShelterAdapter() {
        shelters = new ArrayList<>();
    }

    @Override
    public ShelterAdapter.ShelterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shelter, parent, false);
        return new ShelterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShelterAdapter.ShelterViewHolder holder, int position) {
        holder.bindItem(shelters.get(position));
    }

    @Override
    public int getItemCount() {
        return shelters.size();
    }

    static class ShelterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_shelter_city_text_view)
        TextView cityTextView;

        @BindView(R.id.item_shelter_name_text_view)
        TextView nameTextView;

        ShelterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindItem(Shelter shelter) {
            cityTextView.setText(String.format("%s, %s", shelter.getCountry(), shelter.getState()));
            nameTextView.setText(shelter.getName());
        }
    }
}
