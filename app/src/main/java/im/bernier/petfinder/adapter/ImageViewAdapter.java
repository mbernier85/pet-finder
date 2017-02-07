package im.bernier.petfinder.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import im.bernier.petfinder.model.Photo;
import timber.log.Timber;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Michael on 2017-02-06.
 */

public class ImageViewAdapter extends PagerAdapter {

    private Context context;
    private List<Photo> photos;
    private SparseArray<PhotoViewAttacher> sparseIntArray = new SparseArray<>();

    public ImageViewAdapter(Context context, List<Photo> photos) {
        this.context = context;
        this.photos = photos;
    }

    public void update(List<Photo> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final ImageView imageView = new ImageView(context);
        container.addView(imageView);

        Picasso.with(context).load(photos.get(position).getValue()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                imageView.setImageBitmap(bitmap);
                PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imageView);
                sparseIntArray.append(position, photoViewAttacher);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Timber.e("onBitmapFailed");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        return imageView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
        PhotoViewAttacher photoViewAttacher = sparseIntArray.get(position);
        if (photoViewAttacher != null) {
            sparseIntArray.get(position).cleanup();
            container.getViewTreeObserver().removeOnGlobalLayoutListener(sparseIntArray.get(position));
        }
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
