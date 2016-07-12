package im.bernier.petfinder.activity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.bernier.petfinder.R;
import im.bernier.petfinder.model.Pet;
import im.bernier.petfinder.presenter.PetPresenter;
import im.bernier.petfinder.view.PetView;

/**
 * Created by Michael on 2016-07-09.
 */

public class PetActivity extends AppCompatActivity  implements PetView {

    @BindView(R.id.pet_breed)
    TextView breedTextView;

    @BindView(R.id.pet_name)
    TextView nameTextView;

    @BindView(R.id.pet_description)
    TextView descriptionTextView;

    @BindView(R.id.pet_toolbar)
    Toolbar toolbar;

    @BindView(R.id.pet_image_view)
    ImageView petImageView;

    private PetPresenter presenter;
    private Point size = new Point();
    private float height;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        getWindowManager().getDefaultDisplay().getSize(size);
        height = getResources().getDimension(R.dimen.activity_pet_image_view_height);

        presenter = new PetPresenter();
        presenter.setView(this);
        presenter.onAttach();
    }

    @Override
    public void updateUi(Pet pet) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(pet.getName());
        }
        nameTextView.setText(pet.getName());
        breedTextView.setText(pet.getBreed());
        descriptionTextView.setText(pet.getDescription());
        String url = pet.getMedia().getThumbnail();
        if (url != null) {
            Picasso.with(this).load(url).resize(size.x, (int)height).centerCrop().into(petImageView);
        }
    }

    @OnClick(R.id.pet_image_view)
    void onImageClick() {
        presenter.onImageClick();
    }

    @Override
    public void openImageViewer(Pet pet) {
        startActivity(new Intent(this, ImageViewerActivity.class));
    }
}
