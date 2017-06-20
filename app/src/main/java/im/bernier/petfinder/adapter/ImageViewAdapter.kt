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

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v4.view.PagerAdapter
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

import im.bernier.petfinder.model.Photo
import timber.log.Timber
import uk.co.senab.photoview.PhotoViewAttacher

/**
 * Created by Michael on 2017-02-06.
 */

class ImageViewAdapter(private val context: Context, private var photos: List<Photo>) : PagerAdapter() {
    private val sparseIntArray = SparseArray<PhotoViewAttacher>()

    fun update(photos: List<Photo>) {
        this.photos = photos
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
        container.addView(imageView)

        Picasso.with(context).load(photos[position].value).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                imageView.setImageBitmap(bitmap)
                val photoViewAttacher = PhotoViewAttacher(imageView)
                sparseIntArray.append(position, photoViewAttacher)
            }

            override fun onBitmapFailed(errorDrawable: Drawable) {
                Timber.e("onBitmapFailed")
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable) {

            }
        })
        return imageView
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
        val photoViewAttacher = sparseIntArray.get(position)
        if (photoViewAttacher != null) {
            sparseIntArray.get(position).cleanup()
            container.viewTreeObserver.removeOnGlobalLayoutListener(sparseIntArray.get(position))
        }
    }

    override fun getCount(): Int {
        return photos.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

}
