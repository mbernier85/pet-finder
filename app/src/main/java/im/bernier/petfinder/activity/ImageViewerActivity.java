package im.bernier.petfinder.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.bernier.petfinder.R;
import im.bernier.petfinder.model.Pet;
import im.bernier.petfinder.presenter.ImageViewerPresenter;
import im.bernier.petfinder.view.ImageViewerView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Michael on 2016-07-12.
 */

public class ImageViewerActivity extends AppCompatActivity implements ImageViewerView {

    @BindView(R.id.image_viewer_image_view)
    ImageView imageView;

    private ImageViewerPresenter presenter;
    private PhotoViewAttacher photoViewAttacher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        ButterKnife.bind(this);

        photoViewAttacher = new PhotoViewAttacher(imageView);
        presenter = new ImageViewerPresenter();
        presenter.setView(this);
        presenter.onAttach();

    }

    @Override
    public void updateUi(Pet pet) {
        if (pet.getMedia().getPhotos().size() > 0) {
            Picasso.with(this).load(pet.getMedia().getThumbnail()).into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    photoViewAttacher.update();
                }

                @Override
                public void onError() {

                }
            });
        }
    }
}
