package im.bernier.petfinder.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private ShelterItemListener listener;

    public void setShelters(List<Shelter> shelters) {
        this.shelters = shelters;
        notifyDataSetChanged();
    }

    public interface ShelterItemListener {
        void itemClick(Shelter shelter);
        void phoneClick(Shelter shelter);
        void emailClick(Shelter shelter);
        void directionClick(Shelter shelter);
    }

    public ShelterAdapter(ShelterItemListener listener) {
        this.listener = listener;
        shelters = new ArrayList<>();
    }

    @Override
    public ShelterAdapter.ShelterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shelter, parent, false);
        final ShelterViewHolder holder = new ShelterViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.itemClick(shelters.get(holder.getAdapterPosition()));
                }
            }
        });
        holder.directionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.directionClick(shelters.get(holder.getAdapterPosition()));
                }
            }
        });

        holder.emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.emailClick(shelters.get(holder.getAdapterPosition()));
                }
            }
        });

        holder.phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.phoneClick(shelters.get(holder.getAdapterPosition()));
                }
            }
        });

        return holder;
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

        @BindView(R.id.item_shelter_directions_button)
        Button directionsButton;

        @BindView(R.id.item_shelter_email_text_view)
        TextView emailTextView;

        @BindView(R.id.item_shelter_phone_text_view)
        TextView phoneTextView;

        @BindView(R.id.item_shelter_phone_button)
        Button phoneButton;

        @BindView(R.id.item_shelter_email_button)
        Button emailButton;

        ShelterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindItem(Shelter shelter) {
            StringBuilder builder = new StringBuilder();
            if (!TextUtils.isEmpty(shelter.getAddress1())) {
                builder.append(shelter.getAddress1());
                builder.append(", ");
            }
            builder.append(String.format("%s, %s, %s", shelter.getZip(), shelter.getState(), shelter.getCountry()));
            cityTextView.setText(builder);
            nameTextView.setText(shelter.getName());

            if (TextUtils.isEmpty(shelter.getEmail())) {
                emailTextView.setVisibility(View.GONE);
                emailButton.setVisibility(View.GONE);
            } else {
                emailTextView.setVisibility(View.VISIBLE);
                emailButton.setVisibility(View.VISIBLE);
                emailTextView.setText(shelter.getEmail());
            }

            if (TextUtils.isEmpty(shelter.getPhone())) {
                phoneButton.setVisibility(View.GONE);
                phoneTextView.setVisibility(View.GONE);
            } else {
                phoneTextView.setVisibility(View.VISIBLE);
                phoneButton.setVisibility(View.VISIBLE);
                phoneTextView.setText(shelter.getPhone());
            }
        }
    }
}
